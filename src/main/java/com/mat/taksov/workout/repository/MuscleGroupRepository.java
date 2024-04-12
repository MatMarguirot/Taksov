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
    List<MuscleGroup> findByWorkoutSessionsId(String workoutSessionId);
}
