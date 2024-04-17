package com.mat.taksov.workout.dto.ExerciseSet;

//import com.mat.taksov.workout.model.ExerciseSet;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSetCreateRequest {
    @Nullable
    private String id;
    @JsonAlias("exercise_id")
    @NotNull
    @Valid
    private String exerciseId = "";

    private String description = "";
    private long weight;
    private long reps;

    @JsonAlias("workout_session_id")
    @Nullable
    private String workoutSessionId = "";
}

