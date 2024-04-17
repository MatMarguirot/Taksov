package com.mat.taksov.workout.dto.Exercise;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mat.taksov.workout.dto.MuscleGroup.MuscleGroupDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseCreateRequest {
    @NotNull
    @NotBlank
    private String name;
    @JsonAlias("muscle_group")
    private MuscleGroupDto muscleGroup;
}
