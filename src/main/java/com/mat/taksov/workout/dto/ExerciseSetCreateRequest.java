package com.mat.taksov.workout.dto;

//import com.mat.taksov.workout.model.ExerciseSet;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.MuscleGroup;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSetCreateRequest {
    @JsonAlias("exercise")
    @NotNull
    @Valid
    private ExerciseDto exerciseDto;

    private String description = "";
    private long weight;
    private long reps;
    @JsonAlias("workout_session")
    @Nullable
    private String workoutSessionId = "";
}

