package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.workout.dto.MuscleGroup.MuscleGroupDto;
import com.mat.taksov.workout.model.enums.WorkoutStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

public interface IWorkoutSessionResponse {
    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Instant getStartTime();

    public void setStartTime(Instant startTime);

    public Instant getEndTime();

    public void setEndTime(Instant endTime);

    public WorkoutStatus getStatus();

    public void setStatus(WorkoutStatus status);

    public String getUserId();

    public void setUserId(String userId);

    public Duration getDuration();

    public void setDuration(Duration duration);

//    public Set<ExerciseSetResponse> getExerciseSets();

//    public void setExerciseSets(Set<ExerciseSetResponse> exerciseSets);

//    public Set<MuscleGroup> getMuscleGroups();

    public void setMuscleGroups(Set<MuscleGroupDto> muscleGroups);
}
