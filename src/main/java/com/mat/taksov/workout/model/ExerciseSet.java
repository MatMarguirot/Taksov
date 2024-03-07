package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.*;
import com.mat.taksov.user.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "exercise_set")
public class ExerciseSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Nullable
    private String description = "";

    private long weight;

    private long reps;

    @ManyToOne(targetEntity = WorkoutSession.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", referencedColumnName = "id", nullable = false)
    private WorkoutSession workoutSession;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "muscle_group_id")
//    private MuscleGroup muscleGroup;
//
//    public void updateMuscleGroup(){
//        this.muscleGroup = this.exercise.getMuscleGroup();
//    }



//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "exercise_set_muscle_group",
//            joinColumns = @JoinColumn(name = "exercise_set_id"),
//            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
//    )
////    @JsonManagedReference("ExerciseBlock_MuscleGroup")
//    @Nullable
//    private Set<MuscleGroup> muscleGroups = new HashSet<>();
}

