package com.mat.taksov.workout.service;

import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.ExerciseSetResponse;
import com.mat.taksov.workout.dto.mapper.ExerciseSetMapper;
import com.mat.taksov.workout.exception.ExerciseNotFoundException;
import com.mat.taksov.workout.exception.ExerciseSetNotFoundException;
import com.mat.taksov.workout.exception.WorkoutNotFoundException;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.repository.ExerciseRepository;
import com.mat.taksov.workout.repository.ExerciseSetRepository;
import com.mat.taksov.workout.repository.MuscleGroupRepository;
import com.mat.taksov.workout.repository.WorkoutSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ExerciseSetService {
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseService exerciseService;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseSetMapper exerciseSetMapper;
    private final WorkoutSessionService workoutSessionService;
    @Transactional(rollbackFor = Exception.class)
    public ExerciseSetResponse createExerciseSet(
            ExerciseSetCreateRequest exerciseSetCreateRequest,
            String workoutSessionId,
            String userId
    ){

        // obtiene referencia
        WorkoutSession workoutSession = workoutSessionService.getWorkoutSessionModelByIdAndUsername(workoutSessionId, userId);

        // llama al servicio de ejercicio para crear el ejercicio si no existe
        Exercise exercise = exerciseService.findOrCreateExerciseModelByExerciseDto(exerciseSetCreateRequest.getExerciseDto());

        ExerciseSet exerciseSet = exerciseSetMapper.toExerciseSet(exerciseSetCreateRequest, exercise);
        exerciseSet.setWorkoutSession(workoutSession);
        ExerciseSet createdExerciseSet = exerciseSetRepository.save(exerciseSet);

        workoutSessionService.updateWorkoutSessionMuscleGroup(workoutSession, exerciseSet.getExercise().getMuscleGroup());

        return exerciseSetMapper.toGetExerciseSetResponse(exerciseSet);
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

    public void deleteExerciseSet(String exerciseSetId, String userId){
        ExerciseSet exerciseSet = exerciseSetRepository.findByIdAndUserId(exerciseSetId, userId).orElseThrow(ExerciseSetNotFoundException::new);
        exerciseSetRepository.delete(exerciseSet);
    }

}
