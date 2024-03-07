package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.user.model.Task;
import com.mat.taksov.workout.dto.MuscleGroupDto;
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
public class WorkoutSessionUpdateNameRequest {
    @Size(min = 0, max = 100, message = "Nombre no puede tener mas de 100 caracteres")
    private String name;

}
