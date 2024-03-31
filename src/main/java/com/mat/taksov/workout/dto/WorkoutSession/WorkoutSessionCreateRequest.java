package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.user.model.Task;
import com.mat.taksov.workout.dto.MuscleGroupDto;
import com.mat.taksov.workout.model.ExerciseSet;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionCreateRequest {
    @Size(min = 0, max = 30, message = "Nombre no puede tener mas de 30 caracteres")
    private String name = "Workout";
    @Size(min = 0, max = 100, message = "Descripcion no puede tener mas de 100 caracteres")
    private String description = "";
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime = null;
    private WorkoutStatus status = WorkoutStatus.TO_DO;
    private String user_id;
    private Duration duration = Duration.ZERO;
    private Set<MuscleGroupDto> muscleGroups = new HashSet<>();
}
