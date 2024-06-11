package com.mat.taksov.workout.dto.mapper;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.ExerciseSet.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionWithSetsResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.WorkoutSession;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WorkoutSessionMapper {
    private final ModelMapper modelMapper;
    private final ExerciseSetMapper exerciseSetMapper;

    public WorkoutSession toWorkoutSession(WorkoutSessionCreateRequest workoutSessionCreateRequest, User user) {
        // mapear sets
//        Set<ExerciseSet> exerciseSets =
//                !workoutSessionCreateRequest.getExerciseSets().isEmpty() ?
//                        workoutSessionCreateRequest.getExerciseSets()
//                                .stream()
//                                .map(exerciseSetMapper::toExerciseSet)
//                                .collect(Collectors.toSet())
//                        : null;

        // si tiene sets, deben estar asociados correctamente por WorkoutSessionId
        if(workoutSessionCreateRequest.getExerciseSets() == null) workoutSessionCreateRequest.setExerciseSets(new HashSet<ExerciseSetCreateRequest>());
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

    public WorkoutSessionWithSetsResponse toGetWorkoutSessionFullResponse(WorkoutSession workoutSession){
        WorkoutSessionWithSetsResponse res = modelMapper.map(workoutSession, WorkoutSessionWithSetsResponse.class);
        res.setUserId(workoutSession.getUser().getId());
        return res;
    }

    public WorkoutSessionResponse toGetWorkoutSessionResponse(WorkoutSession workoutSession){
        WorkoutSessionResponse res = modelMapper.map(workoutSession, WorkoutSessionResponse.class);
        res.setUserId(workoutSession.getUser().getId());
        return res;
    }

    public List<WorkoutSessionResponse> toWorkoutSessionResponseList(List<WorkoutSession> workoutSessions){
        return workoutSessions
                .stream()
                .map(this::toGetWorkoutSessionResponse)
                .toList();
    }
}
