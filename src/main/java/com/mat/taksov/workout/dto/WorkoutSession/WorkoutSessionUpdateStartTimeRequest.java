package com.mat.taksov.workout.dto.WorkoutSession;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionUpdateStartTimeRequest {
    @JsonAlias("start_time")
    private LocalDateTime startTime;
}
