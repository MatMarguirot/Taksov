package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, String> {
    Optional<MuscleGroup> findByName(String name);

    boolean existsById(String id);
    boolean existsByName(String name);
    List<MuscleGroup> findAll();
}
