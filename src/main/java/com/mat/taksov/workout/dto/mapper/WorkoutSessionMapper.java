package com.mat.taksov.workout.dto.mapper;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionFullResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.WorkoutSession;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorkoutSessionMapper {
    private final ModelMapper modelMapper;

    public WorkoutSession toWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, User user) {
        // si tiene sets, deben estar asociados correctamente por WorkoutSessionId
        WorkoutSession workoutSession = modelMapper.map(workoutSessionCreateRequest, WorkoutSession.class);
        workoutSession.setUser(user);
        return workoutSession;
    }

    public WorkoutSession toWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, String userId) {
        // si tiene sets, deben estar asociados correctamente por WorkoutSessionId
        WorkoutSession workoutSession = modelMapper.map(workoutSessionCreateRequest, WorkoutSession.class);
        User user = new User();
        user.setId(userId);
        workoutSession.setUser(user);
        return workoutSession;
    }

    public WorkoutSessionFullResponse toGetWorkoutSessionFullResponse(WorkoutSession workoutSession){
        WorkoutSessionFullResponse res = modelMapper.map(workoutSession, WorkoutSessionFullResponse.class);
        res.setUserId(workoutSession.getUser().getId());
        return res;
    }

    public WorkoutSessionResponse toGetWorkoutSessionResponse(WorkoutSession workoutSession){
        WorkoutSessionResponse res = modelMapper.map(workoutSession, WorkoutSessionResponse.class);
        res.setUserId(workoutSession.getUser().getId());
        return res;
    }
}
