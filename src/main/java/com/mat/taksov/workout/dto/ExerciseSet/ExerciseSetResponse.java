package com.mat.taksov.workout.dto.ExerciseSet;

//import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.dto.Exercise.ExerciseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
