package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mat.taksov.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "day_muscle_group")
public class ProgramDay implements WorkoutSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "program_day_muscle_group",
            joinColumns = @JoinColumn(name = "program_day_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"program_day_id", "muscle_group_id"})
    )
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

}
