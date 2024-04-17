package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.workout.model.enums.WorkoutStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse { // implements IWorkoutSessionResponse {
    private String id;
    private String name;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private WorkoutStatus status;
    private String userId;
    //    private User user;
    private Duration duration;
//    private Set<ExerciseSetResponse> exerciseSets = new HashSet<>();
//    private Set<MuscleGroupDto> muscleGroups;
//    private Set<MuscleGroup> muscleGroups;
}



