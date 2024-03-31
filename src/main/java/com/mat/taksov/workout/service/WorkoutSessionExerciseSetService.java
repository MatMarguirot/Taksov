package com.mat.taksov.workout.service;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionFullResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.dto.mapper.ExerciseSetMapper;
import com.mat.taksov.workout.dto.mapper.WorkoutSessionMapper;
import com.mat.taksov.workout.exception.ExerciseNotFoundException;
import com.mat.taksov.workout.exception.ExerciseSetNotFoundException;
import com.mat.taksov.workout.exception.WorkoutNotFoundException;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.model.WorkoutsessionUpdateExerciseSetsRequest;
import com.mat.taksov.workout.repository.ExerciseRepository;
import com.mat.taksov.workout.repository.ExerciseSetRepository;
import com.mat.taksov.workout.repository.WorkoutSessionRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WorkoutSessionExerciseSetService {
    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetMapper exerciseSetMapper;
    private final WorkoutSessionMapper workoutSessionMapper;

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse updateWorkoutSessionExerciseSets(
            String workoutSessionId,
            String userId,
            List<ExerciseSetCreateRequest> updateExerciseSetsRequest
//            WorkoutsessionUpdateExerciseSetsRequest updateExerciseSetsRequest
    ){
        // encuentra workoutsession
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);

        // obtiene exercise sets
        Set<ExerciseSet> exerciseSets = updateExerciseSetsRequest.stream()
//                .map(exerciseSetMapper::toExerciseSet)
                .map((exerciseSet) -> {
                    // obtiene ejercicios completos desde db, para poder actualizar muscleGroups
                    Exercise exercise = exerciseRepository.findById(exerciseSet.getExerciseId()).orElseThrow(ExerciseNotFoundException::new);
                    return exerciseSetMapper.toExerciseSet(exerciseSet, exercise);
                })
                .collect(Collectors.toSet());

        // asigna exercise sets a workoutsession y actualiza muscle groups
        workoutSession.setExerciseSets(exerciseSets); // problem
        WorkoutSession persistedWorkoutSession = workoutSessionRepository.save(workoutSession);
        persistedWorkoutSession.getExerciseSets();
//        return workoutSessionMapper.toGetWorkoutSessionResponse(persistedWorkoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(persistedWorkoutSession);
    }

}
