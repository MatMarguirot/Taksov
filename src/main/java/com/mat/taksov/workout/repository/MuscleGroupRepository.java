package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, String> {
    Optional<MuscleGroup> findByName(String name);

    boolean existsById(String id);
    boolean existsByName(String name);
    List<MuscleGroup> findAll();

//    @Query("SELECT mg FROM MuscleGroup mg JOIN WorkoutSession ws ON mg.workoutSessions WHERE ws.id = :workoutSessionId")
//    List<MuscleGroup> findByWorkoutSessionId(@Param("workoutSessionId") String workoutSessionId);

    @Query("""
            SELECT mg FROM WorkoutSession ws
            JOIN ws.exerciseSets es
            JOIN es.exercise e
            JOIN e.muscleGroup mg
            WHERE ws.id = :ws_id
            """)
    List<MuscleGroup> findByWorkoutSessionId(@Param("ws_id") String workoutSessionId);

//    @Query("""
//            SELECT mg FROM WorkoutSession ws
//            JOIN ws.exerciseSets es
//            JOIN es.exercise e
//            JOIN es.muscleGroup mg
//            WHERE ws.id = :id
//            """)
}
