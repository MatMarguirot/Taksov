package com.mat.taksov.workout.service;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.ExerciseSetResponse;
import com.mat.taksov.workout.dto.ExerciseSetsResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionFullResponse;
import com.mat.taksov.workout.dto.mapper.ExerciseSetMapper;
import com.mat.taksov.workout.dto.mapper.WorkoutSessionMapper;
import com.mat.taksov.workout.exception.ExerciseNotFoundException;
import com.mat.taksov.workout.exception.ExerciseSetNotFoundException;
import com.mat.taksov.workout.exception.WorkoutNotFoundException;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.repository.ExerciseRepository;
import com.mat.taksov.workout.repository.ExerciseSetRepository;
import com.mat.taksov.workout.repository.MuscleGroupRepository;
import com.mat.taksov.workout.repository.WorkoutSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WorkoutSessionExerciseSetService {
    private final WorkoutSessionService workoutSessionService;
    private final WorkoutSessionRepository workoutSessionRepository; // replace for workoutSessionService
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetMapper exerciseSetMapper;
    private final WorkoutSessionMapper workoutSessionMapper;
    private final MuscleGroupRepository muscleGroupRepository;

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse createWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, String userId){
        Set<ExerciseSet> exerciseSets;

        // referencia de usuario
        User user = new User();
        user.setId(userId);

        WorkoutSession workoutSession = workoutSessionMapper.toWorkoutSession(workoutSessionCreateRequest, user);


        // asigna referencia a workoutSession a nested exerciseSets
        if(workoutSessionCreateRequest.getExerciseSets() != null && !workoutSessionCreateRequest.getExerciseSets().isEmpty()){
            exerciseSets = workoutSession.getExerciseSets().stream().map((exerciseSet) -> {
                exerciseSet.setWorkoutSession(workoutSession);
                return exerciseSet;
            }).collect(Collectors.toSet());
            workoutSession.setExerciseSets(exerciseSets);
        }

        // crea workoutSession
        WorkoutSession createdWorkoutSession = workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(createdWorkoutSession);
//        WorkoutSessionFullResponse createdWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionCreateRequest, userId);
//        if()
    }

    @Transactional(rollbackFor = Exception.class)
    public ExerciseSetResponse addExerciseSet(
            ExerciseSetCreateRequest exerciseSetCreateRequest,
            String userId,
            String workoutSessionId
    ){
        // obtiene workoutSession
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);

        // asocia exerciseSet a workoutSession si no trae su id
        if(exerciseSetCreateRequest.getWorkoutSessionId() == null || exerciseSetCreateRequest.getWorkoutSessionId().isBlank()){
            exerciseSetCreateRequest.setWorkoutSessionId(workoutSessionId);
        }

        // obtiene exercise
        Exercise exercise = exerciseRepository.findById(exerciseSetCreateRequest.getExerciseId()).orElseThrow(ExerciseNotFoundException::new);

        // guarda ExerciseSet
        ExerciseSet persistedExerciseSet = exerciseSetRepository.saveAndFlush(exerciseSetMapper.toExerciseSet(exerciseSetCreateRequest, exercise));

        // ve si el MuscleGoup de ExerciseSet se encuentra en Workout.muscleGroups para actualizar
        if(!workoutSession.getMuscleGroups().contains(persistedExerciseSet.getExercise().getMuscleGroup())){
//            muscleGroupRepository.save(persistedExerciseSet.getExercise().getMuscleGroup());
            updateAndPersistWorkoutSessionMuscleGroup(workoutSessionId);

        }
        return exerciseSetMapper.toGetExerciseSetResponse(persistedExerciseSet);
    }

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
                    if(exerciseSet.getId()!=null){
                        return exerciseSetRepository.findByIdAndUserId(exerciseSet.getId(), userId).orElseThrow(ExerciseSetNotFoundException::new);
                    }
                    // obtiene ejercicios completos desde db, para poder actualizar muscleGroups
                    Exercise exercise = exerciseRepository.findById(exerciseSet.getExerciseId()).orElseThrow(ExerciseNotFoundException::new);
                    return exerciseSetMapper.toExerciseSet(exerciseSet, exercise);
                })
                .collect(Collectors.toSet());

        // asigna exercise sets a workoutsession y actualiza muscle groups
        workoutSession.setExerciseSets(exerciseSets); // problem
        WorkoutSession persistedWorkoutSession = workoutSessionRepository.saveAndFlush(workoutSession);
//        return workoutSessionMapper.toGetWorkoutSessionResponse(persistedWorkoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(persistedWorkoutSession);
    }
    @Transactional(rollbackFor = Exception.class)
    public ExerciseSetsResponse updateWorkoutSessionExerciseSets2(
            String workoutSessionId,
            String userId,
            Set<ExerciseSetCreateRequest> updateExerciseSetsRequest
    //            WorkoutsessionUpdateExerciseSetsRequest updateExerciseSetsRequest
    ) {
        // encuentra workoutsession
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);

        // mapeo exerciseSets
        Set<ExerciseSet> exerciseSets = updateExerciseSetsRequest.stream()
                .map((exerciseSet) -> {
                    exerciseSet.setWorkoutSessionId(workoutSessionId);
                    return exerciseSetMapper.toExerciseSet(exerciseSet);
                })
                .collect(Collectors.toSet());

        // persiste exerciseSets
        List<ExerciseSet> persistedExerciseSets = exerciseSetRepository.saveAllAndFlush(exerciseSets);

        // mapeo a exerciseSetResponse
        Set<ExerciseSetResponse> exerciseSetResponses = persistedExerciseSets.stream()
                .map(exerciseSetMapper::toGetExerciseSetResponse)
                .collect(Collectors.toSet());

        // objeto response
        ExerciseSetsResponse res = new ExerciseSetsResponse(workoutSessionId, exerciseSetResponses);
        return res;

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteExerciseSetFromWorkout(
            String workoutSessionId,
            String userId,
            String exerciseSetId

    ) {
        exerciseSetRepository.findByIdAndUserId(exerciseSetId, userId).orElseThrow(ExerciseSetNotFoundException::new);
        exerciseSetRepository.deleteById(exerciseSetId);
        exerciseSetRepository.flush();
        // obtiene workoutsession con exerciseSets
        WorkoutSession workoutSession = workoutSessionRepository.findByIdWithSets(workoutSessionId);
        workoutSession.updateMuscleGroups();
        workoutSessionRepository.save(workoutSession);
    }


    // actualiza los musclegroups de workoutsession
    @Transactional(rollbackFor = Exception.class)
    public void updateAndPersistWorkoutSessionMuscleGroup(
            String workoutSessionId
    ) {
        // obtiene workoutsession con exerciseSets
        WorkoutSession workoutSession = workoutSessionRepository.findByIdWithSets(workoutSessionId);
        workoutSession.updateMuscleGroups();
        workoutSessionRepository.save(workoutSession);
    }
}



