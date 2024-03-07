package com.mat.taksov.workout.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.WorkoutSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuscleGroupDto {
    private String name;
}
