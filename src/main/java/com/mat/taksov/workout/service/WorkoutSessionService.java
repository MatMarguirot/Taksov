package com.mat.taksov.workout.service;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionFullResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.dto.mapper.WorkoutSessionMapper;
import com.mat.taksov.workout.exception.NoWorkoutsForUserException;
import com.mat.taksov.workout.exception.WorkoutIllegalStateException;
import com.mat.taksov.workout.exception.WorkoutNotFoundException;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import com.mat.taksov.workout.repository.WorkoutSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
@Service
public class WorkoutSessionService {
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutSessionMapper workoutSessionMapper;

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse createWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, String userId){
        User user = new User();
        user.setId(userId);
        WorkoutSession createdWorkoutSession = workoutSessionRepository.save(workoutSessionMapper.toWorkoutSession(workoutSessionCreateRequest, user));
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(createdWorkoutSession);
    }

    public Page<WorkoutSessionResponse> getAllWorkoutSessions(Pageable pageable){
        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findAll(pageable);
        Page<WorkoutSessionResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionResponse);
        return workoutSessionsRes;
    }

    @Transactional(readOnly = true)
    public WorkoutSessionFullResponse getWorkoutSessionById(String workoutId){
        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutId).orElseThrow(WorkoutNotFoundException::new);
        WorkoutSessionFullResponse workoutSessionRes = workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
        return workoutSessionRes;
    }

    public Page<WorkoutSessionResponse> getWorkoutSessionsByUser(String userId, Pageable pageable){
        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findByUserId(userId, pageable).orElseThrow(NoWorkoutsForUserException::new);
        Page<WorkoutSessionResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionResponse);
        return workoutSessionsRes;
    }

//    public Page<WorkoutSessionFullResponse> getWorkoutSessionsWithSetsByUser(String userId, Pageable pageable){
//        Page<WorkoutSession> workoutSessions = workoutSessionRepository.findByUserIdWithSets(userId, pageable);
//        Page<WorkoutSessionFullResponse> workoutSessionsRes = workoutSessions.map(workoutSessionMapper::toGetWorkoutSessionFullResponse);
//        return workoutSessionsRes;
//    }
    @Transactional(readOnly = true)
    public WorkoutSessionFullResponse getWorkoutSessionByIdAndUserId(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        WorkoutSessionFullResponse workoutSessionRes = workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
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
//    @Transactional(readOnly = true)
//    public WorkoutSession getWorkoutSessionModelByIdAndUserId(String workoutId, String userId){
//        return workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
//
//    }

//    @Transactional(readOnly = true, rollbackFor = Exception.class)
//    public WorkoutSession getWorkoutSessionReference(String workoutId, String userId){
//        return workoutSessionRepository.getReferenceById(workoutId);
//
//    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse startSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() != WorkoutStatus.TO_DO)
            throw new WorkoutIllegalStateException("TAREA YA HA SIDO" + (workoutSession.getStatus() == WorkoutStatus.IN_PROGRESS ? " INICIADA" : " FINALIZADA"));
        LocalDateTime startTime = LocalDateTime.now();
        if(workoutSession.getEndTime() != null && startTime.isAfter(workoutSession.getEndTime()))
            throw new WorkoutIllegalStateException("LA TAREA NO PUEDE TENER UNA FECHA DE INICIO MAYOR A LA FECHA DE TERMINO");
        workoutSession.setStartTime(startTime);
        workoutSession.setStatus(WorkoutStatus.IN_PROGRESS);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse endSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() != WorkoutStatus.IN_PROGRESS) throw new WorkoutIllegalStateException("TAREA " + (workoutSession.getStatus() == WorkoutStatus.TO_DO ? "AUN NO HA SIDO INICIADA" : "YA FUE FINALIZADA"));
        LocalDateTime endTime = LocalDateTime.now();
        if(endTime != null && endTime.isBefore(workoutSession.getStartTime())) throw new WorkoutIllegalStateException("LA TAREA NO PUEDE TENER UNA FECHA DE TERMINO MENOR A LA FECHA DE INICIO");
        workoutSession.setEndTime(endTime);
//        workoutSession.calculateAndSetDuration();
        workoutSession.setStatus(WorkoutStatus.FINISHED);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toGetWorkoutSessionFullResponse(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionFullResponse resetSession(String workoutId, String userId){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStatus() == WorkoutStatus.TO_DO) throw new WorkoutIllegalStateException("TAREA AUN NO HA SIDO INICIADA");
        workoutSession.setEndTime(null);
        workoutSession.setStartTime(LocalDateTime.now());
//        workoutSession.setDuration(Duration.ZERO);
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
    public WorkoutSessionResponse updateWorkoutSessionStartTime(String workoutSessionId, String userId, LocalDateTime startTime){
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(workoutSessionId, userId).orElseThrow(WorkoutNotFoundException::new);
        if(workoutSession.getStartTime() != null && workoutSession.getStartTime().equals(startTime)) return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSession);
        workoutSession.setStartTime(startTime);
        return workoutSessionMapper.toGetWorkoutSessionResponse(workoutSessionRepository.save(workoutSession));
    }

    @Transactional(rollbackFor = Exception.class)
    public WorkoutSessionResponse updateWorkoutSessionEndTime(String workoutSessionId, String userId, LocalDateTime endTime){
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

    // POJO
    @Transactional(rollbackFor = Exception.class)
    public WorkoutSession updateWorkoutSessionMuscleGroup(WorkoutSession workoutSession, MuscleGroup muscleGroup){
        if(workoutSession.getMuscleGroups().contains(muscleGroup)) return workoutSession;
        workoutSession.addMuscleGroup(muscleGroup);
        return workoutSessionRepository.save(workoutSession);
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

    @Transactional(rollbackFor = Exception.class)
    public void refreshWorkoutSessionMuscleGroups(String workoutSessionId){
        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutSessionId).orElseThrow(WorkoutNotFoundException::new);
        workoutSession.updateMuscleGroups();
        workoutSessionRepository.save(workoutSession);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeExerciseSetsFromWorkoutSession(String workoutSessionId, ExerciseSet exerciseSet) {
        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutSessionId).orElseThrow(WorkoutNotFoundException::new);
        workoutSession.getExerciseSets().remove(exerciseSet);
        workoutSession.updateMuscleGroups();
        workoutSessionRepository.save(workoutSession);
        log.info("WorkoutSession actualizado con éxito.");
    }



//    @Transactional(readOnly = true)
//    public WorkoutSession getAllWorkoutSessionModelsByUserId(String userId){
//        WorkoutSession workoutSession = workoutSessionRepository.findByUser(userId).orElseThrow(WorkoutNotFoundException::new);
//        return workoutSession;
//    }



}
