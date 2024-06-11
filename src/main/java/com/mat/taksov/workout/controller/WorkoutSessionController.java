package com.mat.taksov.workout.controller;

import com.mat.taksov.authentication.service.UserSessionService;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.WorkoutSession.*;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.service.WorkoutSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/workouts")
@RestController
@Tag(name = "WorkoutSession Management (user)")
public class WorkoutSessionController {
    private final WorkoutSessionService workoutSessionService;
    private final UserSessionService userSessionService;

    @PostMapping("/create")
    public ResponseEntity<WorkoutSessionWithSetsResponse> createOwnWorkoutSession (
            @Valid @RequestBody WorkoutSessionCreateRequest workoutSessionCreateRequest,
            @AuthenticationPrincipal User user
    ){
        WorkoutSessionWithSetsResponse createdWorkoutSession = workoutSessionService.createWorkoutSession(workoutSessionCreateRequest, user.getId());
        return ResponseEntity.ok(createdWorkoutSession);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<WorkoutSessionResponse>> getOwnWorkoutSessions(
            @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 5, sort = {"startTime"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionsByUser(user.getId(), pageable));
    }

    // cambiar luego para que traiga tambien los muscle groups
    // para ello llamar a MuscleGroupService
    @GetMapping("/all/full")
    public ResponseEntity<Page<WorkoutSessionWithSetsResponse>> getOwnWorkoutSessionsWithSets(
            @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 5, sort = {"startTime"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionsByUserWithSets(user.getId(), pageable));
    }

    @GetMapping("/{workout_id}")
    public ResponseEntity<WorkoutSessionWithSetsResponse> getWorkoutSessionById(
            @AuthenticationPrincipal User user,
            @PathVariable("workout_id") String workoutId
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionByIdAndUserIdWithSets(workoutId, user.getId()));
    }

    @GetMapping("/{workout_id}/full")
    public ResponseEntity<WorkoutSessionResponse> getWorkoutSessionByIdWithSets(
            @AuthenticationPrincipal User user,
            @PathVariable("workout_id") String workoutId
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionByIdAndUserId(workoutId, user.getId()));
    }

    @GetMapping("/since")
    public List<WorkoutSessionResponse> getSessionsByEndTime(
            @RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        return workoutSessionService.getSessionsByEndTime(endTime);
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
    public ResponseEntity<WorkoutSessionWithSetsResponse> startSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.startSession(workoutId, user.getId()));
    }

    @PostMapping("/{workout_id}/end")
    public ResponseEntity<WorkoutSessionWithSetsResponse> endSession(
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(workoutSessionService.endSession(workoutId, user.getId()));
    }

    @PostMapping("/{workout_id}/reset")
    public ResponseEntity<WorkoutSessionWithSetsResponse> resetSession(
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

    @GetMapping("/current") // changes
    public ResponseEntity<WorkoutSessionWithSetsResponse> getCurrentWorkout(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "withSets", required = false, defaultValue = "false") boolean withSets
    ){
        return ResponseEntity.ok(workoutSessionService.getLatestPendingWorkoutSession(user.getId(), withSets));
    }

}
