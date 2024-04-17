package com.mat.taksov.workout.controller;

import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.MuscleGroup.MuscleGroupBulkDto;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.service.MuscleGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/muscle_group")
@RestController
@EnableMethodSecurity
@Tag(name = "MuscleGroup Management")
public class MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    @GetMapping("/all")
    public ResponseEntity<List<MuscleGroup>> getAllMuscleGroups() {
        return ResponseEntity.ok(muscleGroupService.findAll());
    }

    @GetMapping
    public ResponseEntity<List<MuscleGroup>> getMuscleGroupsByWorkoutId(@RequestParam("workoutSessionId") String workoutSessionId) {
        return ResponseEntity.ok(muscleGroupService.getByWorkoutSessionId(workoutSessionId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<Map<String,String>> bulkUploadMuscleGroups(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody List<MuscleGroupBulkDto> muscleGroupBulkDtos
            ){

        return ResponseEntity.ok(muscleGroupService.createBulkMuscleGroup(muscleGroupBulkDtos));

    }
}
