package com.mat.taksov.workout.service;

import com.mat.taksov.workout.dto.Exercise.ExerciseBulkDto;
import com.mat.taksov.workout.dto.Exercise.ExerciseDto;
import com.mat.taksov.workout.exception.ExerciseNotFoundException;
import com.mat.taksov.workout.exception.InvalidBulkExerciseException;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupService muscleGroupService;
    private final ModelMapper exerciseMapper;

    public Exercise findById(String id) {
        return exerciseRepository.findById(id).orElseThrow(ExerciseNotFoundException::new);
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteById(String id) {
        exerciseRepository.deleteById(id);
    }

    public List<ExerciseDto> findAll() {
        List<Exercise> exercises = exerciseRepository.findAll();
        List<ExerciseDto> exerciseDtoList = exercises.stream().map((exercise) -> exerciseMapper.map(exercise, ExerciseDto.class)).toList();
        log.info("Enviando ejercicios. Items: " + exerciseDtoList.size());
        return exerciseDtoList;
    }

//    public List<Exercise> findAllByMuscleGroupId(String muscleGroupId) {
//        return exerciseRepository.findAllByMuscleGroupId(muscleGroupId).orElseThrow(ExerciseNotFoundException::new);
//    }

    // return models
    public Exercise findExerciseModelById(String id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    public Exercise saveExerciseFromExerciseDto(Exercise exercise) {
        return new Exercise();
    }

    public Exercise findExerciseModelByName(String name) {
        return exerciseRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public Exercise findOrCreateExerciseModelByExerciseDto(ExerciseDto exerciseDto) {
        // si lo encuentra lo obtiene
        if(exerciseRepository.existsByName(exerciseDto.getName())) return exerciseRepository.findByName(exerciseDto.getName());

        // crea nuevo ejercicio
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());

        //obtiene o crea musclegroup
        MuscleGroup muscleGroup = muscleGroupService.findOrCreateMuscleGroupModelByName(exerciseDto.getMuscleGroup().getName());

        //inserta ejercicio con referencia a muscle group encontrado/nuevo
        exercise.setMuscleGroup(muscleGroup);

        Exercise newExercise = exerciseRepository.save(exercise);
        return newExercise;
//        return exerciseRepository.save(exercise);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Exercise> createBulkExercise(List<ExerciseBulkDto> exercisesDto){
        List<String> insertedExercises = new ArrayList<>();
        List<Exercise> exercises = exercisesDto.stream()
                .filter((exercise) -> {
                        if(insertedExercises.contains(exercise.getName())) return false;
                        if(exerciseRepository.existsByName(exercise.getName()) || !muscleGroupService.existsByName(exercise.getMuscleGroupName())) return false;
                        insertedExercises.add(exercise.getName());
                        return true;
                }
                )
                .map((exercise) -> {
                    MuscleGroup muscleGroup = muscleGroupService.findMuscleGroupModelByName(exercise.getMuscleGroupName());
                    return Exercise.builder()
                            .name(exercise.getName())
                            .muscleGroup(muscleGroup)
                            .build();
                }).toList();
        if(exercises.isEmpty()) throw new InvalidBulkExerciseException();
        return exerciseRepository.saveAll(exercises);
    }



//    public Exercise saveExerciseModel(Exercise exercise) {
//        return exerciseRepository.save(exercise);
//    }

//    public void deleteExerciseModelById(String id) {
//        exerciseRepository.deleteById(id);
//    }

    public List<Exercise> findAllExerciseModel() {
        return exerciseRepository.findAll();
    }

//    public List<Exercise> findAllExerciseModelByMuscleGroupId(String muscleGroupId) {
//        return exerciseRepository.findAllByMuscleGroupId(muscleGroupId).orElseThrow(ExerciseNotFoundException::new);
//    }

    public void deleteAll(){
        exerciseRepository.deleteAll();
    }
}
