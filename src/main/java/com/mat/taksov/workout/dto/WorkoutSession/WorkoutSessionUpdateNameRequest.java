package com.mat.taksov.workout.dto.WorkoutSession;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionUpdateNameRequest {
    @Size(min = 0, max = 100, message = "Nombre no puede tener mas de 100 caracteres")
    private String name;

}
