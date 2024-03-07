package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

//    @OneToMany(mappedBy = "muscleGroup", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Set<Exercise> exercises = new HashSet<>();

//    @ManyToMany(mappedBy = "muscleGroups", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Set<WorkoutSession> workoutSessions = new HashSet<>();

//    @ManyToMany(mappedBy = "muscleGroups", fetch = FetchType.LAZY)
//    private Set<ProgramDay> programDays;
}
