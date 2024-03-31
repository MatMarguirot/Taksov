package com.mat.taksov.workout.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuscleGroupUpdateDto {
    @Nullable
    private String id;
    @Nullable
    private String name;
}
