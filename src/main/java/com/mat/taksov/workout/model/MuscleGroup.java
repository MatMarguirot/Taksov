package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "muscle_group")
public class MuscleGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "muscle_group_name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "muscleGroups", fetch = FetchType.LAZY)
    @JsonBackReference
    @Nullable
    private Set<WorkoutSession> workoutSessions = new HashSet<>();

    public MuscleGroup(String name){
        this.name = name;
    }

//    @OneToMany(mappedBy = "muscleGroup", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Set<Exercise> exercises = new HashSet<>();


//    @ManyToMany(mappedBy = "muscleGroups", fetch = FetchType.LAZY)
//    private Set<ProgramDay> programDays;
}
