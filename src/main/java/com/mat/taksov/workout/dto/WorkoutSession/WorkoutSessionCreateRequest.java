package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.workout.dto.ExerciseSet.ExerciseSetCreateRequest;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionCreateRequest {
    @Size(min = 0, max = 30, message = "Nombre no puede tener mas de 30 caracteres")
    private String name = "Workout";
    @Size(min = 0, max = 100, message = "Descripcion no puede tener mas de 100 caracteres")
    private String description = "";
    private Instant startTime = Instant.now();
    private Instant endTime = null;
    private WorkoutStatus status = WorkoutStatus.TO_DO;
    @Nullable
    private Set<ExerciseSet> exerciseSets;
    private String user_id;
    private Duration duration = Duration.ZERO;
//    private Set<MuscleGroupDto> muscleGroups = new HashSet<>();
}
