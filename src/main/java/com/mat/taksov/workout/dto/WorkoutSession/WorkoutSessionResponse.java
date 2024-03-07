package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.user.model.Task;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse implements IWorkoutSessionResponse {
    private String id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private WorkoutStatus status;
    private String userId;
    //    private User user;
    private Duration duration;
    //    private Set<ExerciseSetResponse> exerciseSets;
    private Set<MuscleGroup> muscleGroups;
}



