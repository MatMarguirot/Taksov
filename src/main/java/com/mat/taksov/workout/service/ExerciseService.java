package com.mat.taksov.workout.service;

import com.mat.taksov.workout.dto.ExerciseDto;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupService muscleGroupService;

    public Exercise findById(String id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteById(String id) {
        exerciseRepository.deleteById(id);
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
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

}
