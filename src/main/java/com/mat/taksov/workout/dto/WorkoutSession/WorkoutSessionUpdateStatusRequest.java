package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.user.model.Task;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionUpdateStatusRequest {
    private WorkoutStatus status;
}
