package com.mat.taksov.workout.service;

import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.ExerciseSetResponse;
import com.mat.taksov.workout.dto.mapper.ExerciseSetMapper;
import com.mat.taksov.workout.exception.ExerciseSetNotFoundException;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.repository.ExerciseSetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Service
public class ExerciseSetService {
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseService exerciseService;
    private final ExerciseSetMapper exerciseSetMapper;
    private final WorkoutSessionService workoutSessionService;
    private final WorkoutSessionExerciseSetService workoutSessionExerciseSetService;
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ExerciseSetResponse createExerciseSet(
            ExerciseSetCreateRequest exerciseSetCreateRequest,
            String workoutSessionId,
            String userId
    ){

        // obtiene referencia
        WorkoutSession workoutSession = workoutSessionService.getWorkoutSessionModelByIdAndUserId(workoutSessionId, userId);

        // llama al servicio de ejercicio para crear el ejercicio si no existe
        Exercise exercise = exerciseService.findById(exerciseSetCreateRequest.getExerciseId());

        ExerciseSet exerciseSet = exerciseSetMapper.toExerciseSet(exerciseSetCreateRequest, exercise);
        exerciseSet.setWorkoutSession(workoutSession);
        ExerciseSet createdExerciseSet = exerciseSetRepository.save(exerciseSet);

        // actualiza workoutSession
        workoutSession.addExerciseSet(createdExerciseSet);
        workoutSessionService.updateWorkoutSession(workoutSession);
//        exerciseSet.setWorkoutSession(workoutSession);

//        workoutSessionService.updateWorkoutSessionMuscleGroup(workoutSession, exerciseSet.getExercise().getMuscleGroup());

        return exerciseSetMapper.toGetExerciseSetResponse(createdExerciseSet);
    }

    // agrega ExerciseSet sin actualizar WorkoutSession
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ExerciseSetResponse addExerciseSet(
            ExerciseSetCreateRequest exerciseSetCreateRequest,
            String workoutSessionId,
            String userId
    ){

        // obtiene referencia
        WorkoutSession workoutSession = workoutSessionService.getWorkoutSessionModelByIdAndUserId(workoutSessionId, userId);

        // llama al servicio de ejercicio para crear el ejercicio si no existe
        Exercise exercise = exerciseService.findById(exerciseSetCreateRequest.getExerciseId());

        ExerciseSet exerciseSet = exerciseSetMapper.toExerciseSet(exerciseSetCreateRequest, exercise);
        exerciseSet.setWorkoutSession(workoutSession);
        ExerciseSet persistedExerciseSet = exerciseSetRepository.saveAndFlush(exerciseSet);

        // actualiza WorkoutSession.muscleGroups
//        workoutSessionExerciseSetService.updateAndPersistWorkoutSessionMuscleGroup(workoutSessionId);
        return exerciseSetMapper.toGetExerciseSetResponse(persistedExerciseSet);
    }

    public ExerciseSetResponse getExerciseSetById(String id){
        ExerciseSetResponse exerciseSetResponse = exerciseSetMapper.toGetExerciseSetResponse(exerciseSetRepository.findById(id).orElseThrow(ExerciseSetNotFoundException::new));
        return exerciseSetResponse;
    }

//    @Transactional(readOnly = true)
    public Page<ExerciseSetResponse> getAllExerciseSets(Pageable pageable){
        Page<ExerciseSet> exerciseSets = exerciseSetRepository.findAll(pageable);
        return exerciseSets.map(exerciseSetMapper::toGetExerciseSetResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteExerciseSet(String exerciseSetId, String userId){
        ExerciseSet exerciseSet = exerciseSetRepository.findByIdAndUserId(exerciseSetId, userId).orElseThrow(ExerciseSetNotFoundException::new);
        log.info("Borrando exercise set: \nID:"+exerciseSet.getId()+"\n Ejercicio: "+exerciseSet.getExercise().getName()+"\n MuscleGroup: "+exerciseSet.getExercise().getMuscleGroup().getName());
        workoutSessionService.removeExerciseSetsFromWorkoutSession(exerciseSet.getWorkoutSession().getId(), exerciseSet);
    }
}
