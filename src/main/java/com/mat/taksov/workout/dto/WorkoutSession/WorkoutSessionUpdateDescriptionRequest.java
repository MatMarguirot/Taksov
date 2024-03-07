package com.mat.taksov.workout.dto.WorkoutSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionUpdateDescriptionRequest {
    @Size(min = 0, max = 100, message = "Descripcion no puede tener mas de 100 caracteres")
    private String description;
}
