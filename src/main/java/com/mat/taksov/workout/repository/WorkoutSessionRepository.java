package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.WorkoutSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, String> {

    @Query("select w from WorkoutSession w where w.id = :workout_id and w.user.id = :user_id")
    Optional<WorkoutSession> findByIdAndUserId(@Param("workout_id") String workoutId, @Param("user_id") String userId);
    @Query("SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.exerciseSets WHERE w.user.id = :id")
    Page<WorkoutSession> findByUserIdWithSets(@Param("id") String id, Pageable pageable);

    Optional<Page<WorkoutSession>> findByUserId(String userId, Pageable pageable);
    Optional<List<WorkoutSession>> findByUserId(String userId);

    int countByUserId(String userId);

    void deleteByUserId(String userId);

}
