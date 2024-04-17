package com.mat.taksov.workout.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.model.enums.WorkoutStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    private Instant startTime;

    @Column(nullable = true)
    @Nullable
    private Instant endTime = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutStatus status;// = Status.TO_DO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ExerciseSet> exerciseSets = new HashSet<>(); //

    private Duration duration;

    @CreationTimestamp
    private Instant created;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "workout_session_muscle_group",
//            joinColumns = @JoinColumn(name = "workout_session_id"),
//            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
//    )
//    private Set<MuscleGroup> muscleGroups = new HashSet<>();



    public void calculateAndSetDuration(){
        if(startTime != null && endTime != null && startTime.isBefore(endTime)){
            this.duration = Duration.between(startTime, endTime);
        }else{
            throw new IllegalArgumentException("LA FECHA DE INICIO NO PUEDE SER MAYOR A LA FECHA DE TERMINO");
        }
    }

    public void setExerciseSets(Set<ExerciseSet> exerciseSets){ // maybe not use setter
        try {
            if(exerciseSets.isEmpty()){
            this.exerciseSets = exerciseSets;
            }else{
                this.exerciseSets.clear();
                this.exerciseSets.addAll(exerciseSets);
            }
//            updateMuscleGroups();

        }catch (Exception e) {
            throw e;
        }
    }

    public void addExerciseSet(ExerciseSet exerciseSet){
        exerciseSets.add(exerciseSet);
//        updateMuscleGroups();
    }

//    public void addMuscleGroup(MuscleGroup muscleGroup){
//        if(this.muscleGroups.contains(muscleGroup)) return;
//        this.muscleGroups.add(muscleGroup);
//    }

    public void setEndTime(Instant endTime){
        if(this.startTime == null) return;
        this.endTime = endTime;
        if(endTime == null){
            this.duration = Duration.ZERO;
            return;
        }
        this.calculateAndSetDuration();
    }

    public void setStartTime(Instant startTime){
        this.startTime = startTime;
        if(endTime == null){
            this.duration = Duration.ZERO;
            return;
        }
        if(this.endTime.isBefore(startTime)) this.calculateAndSetDuration();
    }

//    public void updateMuscleGroups(){
//        this.muscleGroups.clear();
//
//        if(this.exerciseSets.isEmpty()){
//            return;
//        }
//
////        this.muscleGroups = this.exerciseSets.stream()
////                .map(exerciseSet -> {
////                    MuscleGroup muscleGroup = exerciseSet.getExercise().getMuscleGroup();
////                    if(muscleGroup == null) throw new IllegalArgumentException("MUSCLE GROUP NO PUEDE ESTAR VACIO");
////                    return muscleGroup;
////                })
////                .distinct()
////                .collect(Collectors.toSet());
//
//        //alt
//        for(ExerciseSet exerciseSet : this.exerciseSets){
//            MuscleGroup muscleGroup = exerciseSet.getExercise().getMuscleGroup();
//            if(muscleGroup == null) throw new IllegalArgumentException("MUSCLE GROUP NO PUEDE ESTAR VACIO");
//            this.muscleGroups.add(muscleGroup);
//        }
//
//    }

}
