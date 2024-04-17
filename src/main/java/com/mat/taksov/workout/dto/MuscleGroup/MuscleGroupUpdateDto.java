package com.mat.taksov.workout.dto.MuscleGroup;

import jakarta.annotation.Nullable;
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
