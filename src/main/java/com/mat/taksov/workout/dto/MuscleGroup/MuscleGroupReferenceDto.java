package com.mat.taksov.workout.dto.MuscleGroup;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MuscleGroupReferenceDto {
    @NotNull
    private String id;

    @Nullable
    private String name;
}
