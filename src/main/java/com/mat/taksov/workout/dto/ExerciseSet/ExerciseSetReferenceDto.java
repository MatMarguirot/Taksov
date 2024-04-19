package com.mat.taksov.workout.dto.ExerciseSet;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// este DTO se pone dentro de DTOs de WorkoutSession para su creacion
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSetReferenceDto {
    @Nullable
    private String id;
    @JsonAlias("exercise_id")
    @NotNull
    @Valid
    private String exerciseId;

    private String description;
    private long weight;
    private long reps;
}
