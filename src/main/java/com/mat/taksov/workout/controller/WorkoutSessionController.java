package com.mat.taksov.workout.controller;

import com.mat.taksov.authentication.service.UserSessionService;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.MuscleGroupDto;
import com.mat.taksov.workout.dto.WorkoutSession.*;
import com.mat.taksov.workout.service.WorkoutSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("workout")
@Controller
public class WorkoutSessionController {
    private final WorkoutSessionService workoutSessionService;
    private final UserSessionService userSessionService;

    @PostMapping("/create")
    public ResponseEntity<WorkoutSessionFullResponse> createOwnWorkoutSession (
            @Valid @RequestBody WorkoutSessionCreateRequest workoutSessionCreateRequest,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.createWorkoutSession(workoutSessionCreateRequest, user.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<WorkoutSessionResponse>> getOwnWorkoutSessions(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionsByUser(user.getId(), pageable));
    }

    @GetMapping("/{workout_id}")
    public ResponseEntity<WorkoutSessionFullResponse> getWorkoutSessionById(
            @AuthenticationPrincipal User user,
            @PathVariable("workout_id") String workoutId
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionByIdAndUserId(workoutId, user.getId()));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearWorkouts(
            @AuthenticationPrincipal User user
    ){
        workoutSessionService.deleteAllWorkoutSessionsForUser(user.getId());
        return ResponseEntity.ok("Se han elminado los entrenamientos del usuario.");
    }

    @DeleteMapping("/{workout_id}")
    public ResponseEntity<String> deleteWorkoutSession(
            @AuthenticationPrincipal User user,
            @PathVariable("workout_id") String workoutSessionId
    ){
        workoutSessionService.deleteWorkoutSessionByIdAndUserId(workoutSessionId, user.getId());
        return ResponseEntity.ok("Workout con ID " + workoutSessionId + " ha sido eliminado con éxito.");
    }

    @PostMapping("/{workout_id}/start")
    public ResponseEntity<WorkoutSessionFullResponse> startSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.startSession(workoutId, user.getId()));
    }

    @PostMapping("/{workout_id}/end")
    public ResponseEntity<WorkoutSessionFullResponse> endSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.endSession(workoutId, user.getId()));
    }

    @PostMapping("/{workout_id}/reset")
    public ResponseEntity<WorkoutSessionFullResponse> resetSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.resetSession(workoutId, user.getId()));
    }

    @PutMapping("/{workout_id}")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionCreateRequest workoutSessionUpdateRequest
    ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSession(workoutId, user.getId(), workoutSessionUpdateRequest));
    }

    @PatchMapping("/{workout_id}/name")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionName(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionUpdateNameRequest updateNameRequest
            ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionName(workoutId, user.getId(), updateNameRequest.getName()));
    }

    @PatchMapping("/{workout_id}/description")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionDescription(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionUpdateDescriptionRequest updateDescriptionRequest
    ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionDescription(workoutId, user.getId(), updateDescriptionRequest.getDescription()));
    }

    @PatchMapping("/{workout_id}/status")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionStatus(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionUpdateStatusRequest updateStatusRequest
    ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionStatus(workoutId, user.getId(), updateStatusRequest.getStatus()));
    }

    @PatchMapping("/{workout_id}/start_time")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionStartTime(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionUpdateStartTimeRequest updateStartTimeRequest
    ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionStartTime(workoutId, user.getId(), updateStartTimeRequest.getStartTime()));
    }

    @PatchMapping("/{workout_id}/end_time")
    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionEndTime(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WorkoutSessionUpdateEndTimeRequest updateEndTimeRequest
    ){
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionEndTime(workoutId, user.getId(), updateEndTimeRequest.getEndTime()));
    }

//    @PatchMapping("/{workout_id}/muscle_group")
//    public ResponseEntity<WorkoutSessionResponse> updateWorkoutSessionMuscleGroup(
//            @PathVariable("workout_id") String workoutId,
//            @AuthenticationPrincipal User user,
//            @Valid @RequestBody MuscleGroupDto updateMuscleGroupRequest
//    ){
//        return ResponseEntity.ok(workoutSessionService.updateWorkoutSessionMuscleGroup(workoutId, user.getId(), updateMuscleGroupRequest));
//    }

}
