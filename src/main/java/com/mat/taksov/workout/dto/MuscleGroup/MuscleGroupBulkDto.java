package com.mat.taksov.workout.dto.MuscleGroup;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuscleGroupBulkDto {
    @JsonAlias("muscle_group_name")
    private String muscleGroupName;
}
