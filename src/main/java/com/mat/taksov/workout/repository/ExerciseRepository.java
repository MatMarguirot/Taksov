package com.mat.taksov.workout.repository;

import com.mat.taksov.workout.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, String> {
    Exercise findByName(String name);
    Boolean existsByName(String name);
//    Optional<List<Exercise>> findAllByMuscleGroupId(String id);
}
