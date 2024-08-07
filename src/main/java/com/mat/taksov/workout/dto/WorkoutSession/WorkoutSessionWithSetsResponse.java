package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.workout.dto.ExerciseSet.ExerciseSetResponse;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionWithSetsResponse { // implements IWorkoutSessionResponse{
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
    @Nullable
    private Instant created;
//    private Set<MuscleGroupDto> muscleGroups;
}


