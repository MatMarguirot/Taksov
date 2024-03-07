package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "exercise_name", nullable = false, unique = true)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    @JsonBackReference
    private Set<ExerciseSet> exerciseSets = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "exercise_set_muscle_group",
//            joinColumns = {@JoinColumn(name = "exercise_set_id")},
//            inverseJoinColumns = {@JoinColumn(name = "muscle_group_id")}
//    )
//    private Set<MuscleGroup> muscleGroups = new HashSet<>();

//    private long time;
}
