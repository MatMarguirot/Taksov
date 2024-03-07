package com.mat.taksov.workout.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.MuscleGroup;
import jakarta.persistence.*;
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
public class ExerciseDto {
    @NotNull
    @NotBlank
    private String name;
    @JsonAlias("muscle_group")
    private MuscleGroupDto muscleGroup;
}
