package com.mat.taksov.workout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSetsResponse {
    private String workoutSessionId = "";
    private Set<ExerciseSetResponse> exerciseSets = new HashSet<>();
}
