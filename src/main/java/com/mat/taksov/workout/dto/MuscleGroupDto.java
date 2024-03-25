package com.mat.taksov.workout.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.WorkoutSession;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuscleGroupDto {
    @Nullable
    private String id;
    @NotBlank
    private String name;
}
