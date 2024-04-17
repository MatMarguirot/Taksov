package com.mat.taksov.workout.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MuscleGroupBulkDto {
    @JsonAlias("muscle_group_name")
    private String muscleGroupName;
}
