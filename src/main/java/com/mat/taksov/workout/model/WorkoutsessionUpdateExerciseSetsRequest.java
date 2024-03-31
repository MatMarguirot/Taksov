package com.mat.taksov.workout.model;


import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class WorkoutsessionUpdateExerciseSetsRequest {
    private List<ExerciseSetCreateRequest> exerciseSets;
}
