package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, String> {
    @Query("SELECT e FROM ExerciseSet e JOIN e.workoutSession ws WHERE e.id = ?1 AND ws.user.id = ?2")
    Optional<ExerciseSet> findByIdAndUserId(String exerciseSetId, String userId);

    Optional<List<ExerciseSet>> findByWorkoutSessionId(String workoutSessionId);
}
