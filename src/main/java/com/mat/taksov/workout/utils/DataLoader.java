//package com.mat.taksov.workout.utils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mat.taksov.workout.dto.MuscleGroupBulkDto;
//import com.mat.taksov.workout.service.ExerciseService;
//import com.mat.taksov.workout.service.MuscleGroupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.Arrays;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final MuscleGroupService muscleGroupService;
//    private final ExerciseService exerciseService;
//
//    @Autowired
//    public DataLoader(MuscleGroupService muscleGroupService, ExerciseService exerciseService) {
//        this.muscleGroupService = muscleGroupService;
//        this.exerciseService = exerciseService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // poblar MuscleGroups
//        ObjectMapper objectMapper = new ObjectMapper();
//        File muscleGroupsJson = new File("com/mat/taksov/workout/utils/muscle_groups.json"); // NOT FOUND
//        MuscleGroupBulkDto[] muscleGroupBulkDtos = objectMapper.readValue(muscleGroupsJson, MuscleGroupBulkDto[].class);
//
//        muscleGroupService.createBulkMuscleGroup(Arrays.stream(muscleGroupBulkDtos).toList());
//    }
//}
