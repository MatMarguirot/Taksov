package com.mat.taksov.workout.dto.WorkoutSession;


import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class WorkoutsessionUpdateExerciseSetsRequest {
    private List<ExerciseSetCreateRequest> exerciseSets;
}
