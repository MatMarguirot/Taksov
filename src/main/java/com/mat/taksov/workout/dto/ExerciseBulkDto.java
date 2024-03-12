package com.mat.taksov.workout.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseBulkDto {
    @NotNull
    @NotBlank
    @JsonAlias("exercise_name")
    private String name;
    @JsonAlias("muscle_group_name")
    private String muscleGroupName;
}
