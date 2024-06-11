package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, String> {

    @Query("select w from WorkoutSession w where w.id = :workout_id and w.user.id = :user_id")
    Optional<WorkoutSession> findByIdAndUserId(@Param("workout_id") String workoutId, @Param("user_id") String userId);

    @Query(
            value = "SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.exerciseSets WHERE w.id = :workout_id",
            countQuery = "SELECT COUNT(w) FROM WorkoutSession w WHERE w.id = :workout_id"
    )
    WorkoutSession findByIdWithSets(@Param("workout_id") String workoutSessionId);

    @Query(
            value = "SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.exerciseSets WHERE w.user.id = :user_id",
            countQuery = "SELECT COUNT(w) FROM WorkoutSession w WHERE w.user.id = :user_id"
    )
    Page<WorkoutSession> findByUserIdWithSets(@Param("user_id") String id, Pageable pageable);

    @Query("SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.exerciseSets WHERE w.user.id = :user_id ORDER BY w.startTime DESC limit 1")
    Optional<WorkoutSession> findLatestWorkoutByUserIdWithSets(@Param("user_id") String id);

    @Query("SELECT w FROM WorkoutSession w WHERE w.user.id = :user_id ORDER BY w.startTime DESC limit 1")
    Optional<WorkoutSession> findLatestWorkoutByUserId(@Param("user_id") String id);

    @Query("SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.exerciseSets WHERE w.user.id = :user_id AND w.status != 'FINISHED' ORDER BY w.startTime DESC limit 1")
    Optional<WorkoutSession> findLatestPendingWorkoutByUserIdWithSets(@Param("user_id") String id);

    Optional<WorkoutSession> findLatestPendingByUserId(@Param("user_id") String id);

    Optional<Page<WorkoutSession>> findByUserId(String userId, Pageable pageable);

    // ESTE HAY QUE DELEGARLO A MUSCLEGROUPS REPOSITORY
//    @Query("SELECT w FROM WorkoutSession w LEFT JOIN FETCH w.muscleGroups WHERE w.user.id = :user_id")
//    Optional<Page<WorkoutSession>> findByUserIdWithMuscleGroups(@Param("user_id") String userId, Pageable pageable);

    Optional<List<WorkoutSession>> findByUserId(String userId);

    Page<WorkoutSession> findByStatusAndUserId(WorkoutStatus status, String userId, Pageable pageable);

    @Query("SELECT w FROM WorkoutSession w WHERE w.status != :status AND w.id = :user_id") // changes
    Page<WorkoutSession> findByExcludeStatusAndUserId(@Param("status") WorkoutStatus excludedStatus, @Param("user_id") String userId, Pageable pageable);

    int countByUserId(String userId);

    void deleteByUserId(String userId);

//    @Query("SELECT w FROM WorkoutSession w WHERE w.endTime >= :endTime")
//    List<WorkoutSession> findAllByEndTimeAfter(@Param("endTime") Instant endTime);

    @Query("SELECT w FROM WorkoutSession w WHERE w.endTime >= :endTime ORDER BY w.endTime ASC")
    List<WorkoutSession> findAllByEndTimeAfter(@Param("endTime") Instant endTime);
}
