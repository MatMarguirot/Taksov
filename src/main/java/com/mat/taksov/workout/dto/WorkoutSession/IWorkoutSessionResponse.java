package com.mat.taksov.workout.dto.WorkoutSession;

import com.mat.taksov.user.model.Task;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.model.enums.WorkoutStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public interface IWorkoutSessionResponse {
    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public LocalDateTime getStartTime();

    public void setStartTime(LocalDateTime startTime);

    public LocalDateTime getEndTime();

    public void setEndTime(LocalDateTime endTime);

    public WorkoutStatus getStatus();

    public void setStatus(WorkoutStatus status);

    public String getUserId();

    public void setUserId(String userId);

    public Duration getDuration();

    public void setDuration(Duration duration);

//    public Set<ExerciseSetResponse> getExerciseSets();

//    public void setExerciseSets(Set<ExerciseSetResponse> exerciseSets);

    public Set<MuscleGroup> getMuscleGroups();

    public void setMuscleGroups(Set<MuscleGroup> muscleGroups);
}
