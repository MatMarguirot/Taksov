package com.mat.taksov.workout.service;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionWithSetsResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.dto.mapper.WorkoutSessionMapper;
import com.mat.taksov.workout.exception.NoWorkoutsForUserException;
import com.mat.taksov.workout.exception.WorkoutIllegalStateException;
import com.mat.taksov.workout.exception.WorkoutNotFoundException;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import com.mat.taksov.workout.repository.WorkoutSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Slf4j
@Service
public class WorkoutSessionService {
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutSessionMapper workoutSessionMapper;
//    private final WorkoutSessionExerciseSetService workoutSessionExerciseSetService;


    // para que funcione, los exerciseSets dentro de workoutSession deben tener id null
    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionWithSetsResponse createWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, String userId){
        User user = new User();
        user.setId(userId);
        WorkoutSession createdWorkoutSession = null;

        // crear workoutSession
        createdWorkoutSession = workoutSessionRepository.saveAndFlush(workoutSessionMapper.toWorkoutSession(workoutSessionCreateRequest, user));

        return workoutSessionMapper.toGetWorkoutSessionFullResponse(createdWorkoutSession);
    }

    public Page<WorkoutSessionResponse> getAllWorkoutSessions(Pageable pageable){
        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findAll(pageable);
        Page<WorkoutSessionResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionResponse);
        return workoutSessionsRes;
    }

    @Transactional(readOnly = true)
    public WorkoutSessionWithSetsResponse getWorkoutSessionById(String workoutId){
        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutId).orElseThrow(WorkoutNotFoundException::new);
        WorkoutSessionWithSetsResponse workoutSessionRes = workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
        return workoutSessionRes;
    }

    public Page<WorkoutSessionResponse> getWorkoutSessionsByUser(String userId, Pageable pageable){
        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findByUserId(userId, pageable).orElseThrow(NoWorkoutsForUserException::new);
        Page<WorkoutSessionResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionResponse);
        return workoutSessionsRes;
    }

    public Page<WorkoutSessionWithSetsResponse> getWorkoutSessionsByUserWithSets(String userId, Pageable pageable){
        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findByUserIdWithSets(userId, pageable);
        Page<WorkoutSessionWithSetsResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionFullResponse);
        return workoutSessionsRes;
    }

    public WorkoutSessionResponse getLatestPendingWorkoutSession(String userId, boolean withSets){
        WorkoutSession workoutSession;
        if (withSets) {
//            workoutSession = workoutSessionRepository.findLatestWorkoutByUserIdWithSets(userId).orElseThrow(WorkoutNotFoundException::new);
            // no trae el que necesito
            workoutSession = workoutSessionRepository.findLatestPendingWorkoutByUserIdWithSets(userId).orElseThrow(WorkoutNotFoundException::new);
        }else workoutSession = workoutSessionRepository.findLatestWorkoutByUserId(userId).orElseThrow(WorkoutNotFoundException::new);
        return this.workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
    }



//    public Page<WorkoutSessionFullResponse> getWorkoutSessionsWithSetsByUser(String userId, Pageable pageable){
//        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findByUserIdWithSets(userId, pageable);
//        Page<WorkoutSessionFullResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionFullResponse);
//        return workoutSessionsRes;
//    }
    @Transactional(readOnly = true)
    public WorkoutSessionWithSetsResponse getWorkoutSessionByIdAndUserIdWithSets(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        WorkoutSessionWithSetsResponse workoutSessionRes = workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
        return workoutSessionRes;
    }

    @Transactional(readOnly = true)
    public WorkoutSessionResponse getWorkoutSessionByIdAndUserId(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        WorkoutSessionResponse workoutSessionRes = workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        return workoutSessionRes;
    }



    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkoutSession(String workoutSessionId){
        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutSessionId).orElseThrow(WorkoutNotFoundException::new);
        workoutSessionRepository.deleteById(workoutSessionId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkoutSessionByIdAndUserId(String workoutSessionId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        workoutSessionRepository.deleteById(workoutSessionId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteAllWorkoutSessionsForUser(String userId){
//        List<WorkoutSession> workoutSession = workoutSessionRepository.findByUserId(userId).orElseThrow(WorkoutNotFoundException::new);
        int count = workoutSessionRepository.countByUserId(userId);
        if(workoutSessionRepository.countByUserId(userId) == 0) throw new WorkoutNotFoundException();
        workoutSessionRepository.deleteByUserId(userId);
    }

    // model methods
    @Transactional(readOnly = true)
    public WorkoutSession getWorkoutSessionModelByIdAndUserId(String workoutId, String userId){
        return workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionWithSetsResponse startSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() != WorkoutStatus.TO_DO)
            throw new WorkoutIllegalStateException("TAREA YA HA SIDO" + (workoutSession.getStatus() == WorkoutStatus.IN_PROGRESS ? " INICIADA" : " FINALIZADA"));
        Instant startTime = Instant.now();
        if(workoutSession.getEndTime() != null && startTime.isAfter(workoutSession.getEndTime()))
            throw new WorkoutIllegalStateException("LA TAREA NO PUEDE TENER UNA FECHA DE INICIO MAYOR A LA FECHA DE TERMINO");
        workoutSession.setStartTime(startTime);
        workoutSession.setStatus(WorkoutStatus.IN_PROGRESS);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionWithSetsResponse endSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() != WorkoutStatus.IN_PROGRESS) throw new WorkoutIllegalStateException("TAREA " + (workoutSession.getStatus() == WorkoutStatus.TO_DO ? "AUN NO HA SIDO INICIADA" : "YA FUE FINALIZADA"));
        Instant endTime = Instant.now();
        if(endTime != null && endTime.isBefore(workoutSession.getStartTime())) throw new WorkoutIllegalStateException("LA TAREA NO PUEDE TENER UNA FECHA DE TERMINO MENOR A LA FECHA DE INICIO");
        workoutSession.setEndTime(endTime);
//        workoutSession.calculateAndSetDuration();
        workoutSession.setStatus(WorkoutStatus.FINISHED);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionWithSetsResponse resetSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() == WorkoutStatus.TO_DO) throw new WorkoutIllegalStateException("TAREA AUN NO HA SIDO INICIADA");
        workoutSession.setEndTime(null);
        workoutSession.setStartTime(Instant.now());
        workoutSession.setStatus(WorkoutStatus.TO_DO);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionName(String workoutSessionId, String userId, String name){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getName().equals(name)) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setName(name);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionDescription(String workoutSessionId, String userId, String description){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getDescription().equals(description)) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setDescription(description);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionStartTime(String workoutSessionId, String userId, Instant startTime){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStartTime() != null && workoutSession.getStartTime().equals(startTime)) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setStartTime(startTime);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionEndTime(String workoutSessionId, String userId, Instant endTime){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getEndTime() != null && workoutSession.getEndTime().equals(endTime)) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setEndTime(endTime);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionStatus(String workoutSessionId, String userId, WorkoutStatus status){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() == status) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setStatus(status);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSession(String workoutSessionId, String userId, WorkoutSessionCreateRequest workoutSessionUpdateRequest){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        workoutSession.setName(workoutSessionUpdateRequest.getName());
        workoutSession.setDescription(workoutSessionUpdateRequest.getDescription());
        workoutSession.setStartTime(workoutSessionUpdateRequest.getStartTime());
        workoutSession.setEndTime(workoutSessionUpdateRequest.getEndTime());
        workoutSession.setStatus(workoutSessionUpdateRequest.getStatus());
        WorkoutSession updatedWorkoutSession = workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionResponse(updatedWorkoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSession(WorkoutSession workoutSession){
        WorkoutSession updatedWorkoutSession = workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionResponse(updatedWorkoutSession);
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void refreshWorkoutSessionMuscleGroups(String workoutSessionId){
//        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutSessionId).orElseThrow(WorkoutNotFoundException::new);
//        workoutSession.updateMuscleGroups();
//        workoutSessionRepository.save(workoutSession);
//    }

    // MOVER ESTA LOGICA A EXERCISESETSSERVICE
//    @Transactional(rollbackFor = Exception.class)
//    public void removeExerciseSetsFromWorkoutSession(String workoutSessionId, ExerciseSet exerciseSet) {
//        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutSessionId).orElseThrow(WorkoutNotFoundException::new);
//        workoutSession.getExerciseSets().remove(exerciseSet);
////        workoutSession.updateMuscleGroups();
//        workoutSessionRepository.save(workoutSession);
//        log.info("WorkoutSession actualizado con éxito.");
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public WorkoutSessionResponse updateWorkoutSessionExerciseSets(String userId, String workoutSessionId, WorkoutsessionUpdateExerciseSetsRequest exerciseSetsRequest){
//        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
//         exerciseSetsRequest.getExerciseSets()
//        workoutSession.setExerciseSets();
//
//        var res = workoutSessionRepository.save(workoutSession);
//        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
//
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public WorkoutSessionResponse addWorkoutSessionExerciseSet(String userId, String workoutSessionId, ExerciseSetCreateRequest exerciseSetsRequest){
//        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
//        workoutSession.setExerciseSets(exerciseSetsRequest.getExerciseSets());
//        var res = workoutSessionRepository.save(workoutSession);
//        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
//
//    }

//    @Transactional(rollbackFor = Exception.class) // changes
//    public Page<WorkoutSession> getPendingWorkoutSessions(String userId, Pageable pageable){
////        Page<WorkoutSession> res = workoutSessionRepository.findByExcludeStatusAndUserId(WorkoutStatus.FINISHED, userId, pageable);
//        Page<WorkoutSession> res = workoutSessionRepository.findAll(WorkoutStatus.FINISHED, userId, pageable);
//        return res;
//    }


//    @Transactional(readOnly = true)
//    public WorkoutSession getAllWorkoutSessionModelsByUserId(String userId){
//        WorkoutSession workoutSession = workoutSessionRepository.findByUser(userId).orElseThrow(WorkoutNotFoundException::new);
//        return workoutSession;
//    }



}
