package com.mat.taksov.workout.controller;

import com.mat.taksov.workout.dto.ExerciseBulkDto;
import com.mat.taksov.workout.dto.ExerciseDto;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("exercise")
@Controller
@EnableMethodSecurity
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseDto>> getAllExercises(){
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @PostMapping("/bulk")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Exercise>> loadExercises(@Valid @RequestBody List<ExerciseBulkDto> exerciseBulkDto){
        return ResponseEntity.ok(exerciseService.createBulkExercise(exerciseBulkDto));
    }
}
