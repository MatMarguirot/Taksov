package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.workout.dto.ExerciseSetResponse;
import com.mat.taksov.workout.dto.MuscleGroupDto;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionFullResponse { // implements IWorkoutSessionResponse{
    private String id;
    private String name;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private WorkoutStatus status;
    private String userId;
//    private User user;
    private Duration duration;
    private Set<ExerciseSetResponse> exerciseSets;
//    private Set<MuscleGroupDto> muscleGroups;
}


