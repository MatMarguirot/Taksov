package com.mat.taksov.workout.dto.MuscleGroup;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuscleGroupDto {
    @Nullable
    private String id;
    @NotBlank
    private String name;
}
