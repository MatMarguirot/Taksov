package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mat.taksov.user.model.Task;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "workout_session")
public class WorkoutSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = true, length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = true)
    @Nullable
    private LocalDateTime endTime = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutStatus status;// = Status.TO_DO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ExerciseSet> exerciseSets = new HashSet<>();

    private Duration duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workout_session_muscle_group",
            joinColumns = @JoinColumn(name = "workout_session_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

    public void calculateAndSetDuration(){
        if(startTime != null && endTime != null && startTime.isBefore(endTime)){
            this.duration = Duration.between(startTime, endTime);
        }else{
            throw new IllegalArgumentException("LA FECHA DE INICIO NO PUEDE SER MAYOR A LA FECHA DE TERMINO");
        }
    }

    public void addMuscleGroup(MuscleGroup muscleGroup){
        if(this.muscleGroups.contains(muscleGroup)) return;
        this.muscleGroups.add(muscleGroup);
    }

    public void setEndTime(LocalDateTime endTime){
        if(this.startTime == null) return;
        this.endTime = endTime;
        if(endTime == null){
            this.duration = Duration.ZERO;
            return;
        }
        this.calculateAndSetDuration();
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
        if(endTime == null){
            this.duration = Duration.ZERO;
            return;
        }
        if(this.endTime.isBefore(startTime)) this.calculateAndSetDuration();
    }

    public void updateMuscleGroups(){
        if(this.exerciseSets.isEmpty()){
            return;
        }
        this.muscleGroups.clear();

        this.muscleGroups = this.exerciseSets.stream()
                .map(exerciseSet -> {
                    MuscleGroup muscleGroup = exerciseSet.getExercise().getMuscleGroup();
                    if(muscleGroup == null) throw new IllegalArgumentException("MUSCLE GROUP NO PUEDE ESTAR VACIO");
                    return muscleGroup;
                })
                .distinct()
                .collect(Collectors.toSet());

    }

}
