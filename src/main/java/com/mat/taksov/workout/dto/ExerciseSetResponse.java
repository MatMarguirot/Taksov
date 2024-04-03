package com.mat.taksov.workout.dto;

//import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.MuscleGroup;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSetResponse {
    private String id = "";
    @NotNull
    private ExerciseDto exercise;
    private String description = "";
    private long weight;
    private long reps;
    private String workoutSessionId = "";
    private Date created;
//    private String muscleGroup;
//    private MuscleGroup muscleGroups;
}
