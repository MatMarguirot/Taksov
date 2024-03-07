package com.mat.taksov.workout.controller;

import com.mat.taksov.authentication.service.UserSessionService;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionCreateRequest;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionFullResponse;
import com.mat.taksov.workout.dto.WorkoutSession.WorkoutSessionResponse;
import com.mat.taksov.workout.service.WorkoutSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
//@RequestMapping("user/{user_id}/workout")
@Controller
@EnableMethodSecurity
public class AdminWorkoutSessionController {
    private final WorkoutSessionService workoutSessionService;
    private final UserSessionService userSessionService;
    private final String urlString = "admin/user/{user_id}/workout";
    private final String workoutUrlString = "admin/workout";

    @PostMapping(urlString+"/create")
    public ResponseEntity<WorkoutSessionFullResponse> createWorkoutSessionForUser (
            @Valid @RequestBody WorkoutSessionCreateRequest workoutSessionCreateRequest,
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId){
        return ResponseEntity.ok(workoutSessionService.createWorkoutSession(workoutSessionCreateRequest, userId));
    }

    @GetMapping("admin/workouts")
    public ResponseEntity<Page<WorkoutSessionResponse>> getAllWorkoutSessions(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ){
        return ResponseEntity.ok(workoutSessionService.getAllWorkoutSessions(pageable));
    }

    @GetMapping(urlString+"/all")
    public ResponseEntity<Page<WorkoutSessionResponse>> getAllWorkoutSessionsByUser(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            Pageable pageable
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionsByUser(userId, pageable));
    }

    @GetMapping(urlString+"/{workout_id}")
    public ResponseEntity<WorkoutSessionFullResponse> getWorkoutSessionByIdAndUser(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionByIdAndUserId(workoutId, userId));
    }

    @GetMapping(workoutUrlString+"/{workout_id}")
    public ResponseEntity<WorkoutSessionFullResponse> getWorkoutSessionById(
            @AuthenticationPrincipal User user,
            @PathVariable("workout_id") String workoutId
    ){
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionById(workoutId));
    }

    @DeleteMapping(urlString+"/clear")
    public ResponseEntity<String> clearWorkoutsByUser(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId
    ){
        workoutSessionService.deleteAllWorkoutSessionsForUser(userId);
        return ResponseEntity.ok("Se han elminado los entrenamientos del usuario.");
    }

    @DeleteMapping(urlString+"/{workout_id}")
    public ResponseEntity<String> deleteWorkoutSessionByIdAndUserId(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutSessionId){
        workoutSessionService.deleteWorkoutSessionByIdAndUserId(workoutSessionId, userId);
        return ResponseEntity.ok("Workout con ID " + workoutSessionId + " ha sido eliminado con éxito.");
    }

    @PostMapping(urlString+"/{workout_id}/start")
    public ResponseEntity<WorkoutSessionFullResponse> startSession(
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(workoutSessionService.startSession(workoutId, userId));
    }

    @PostMapping(urlString+"/{workout_id}/end")
    public ResponseEntity<WorkoutSessionFullResponse> endSession(
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(workoutSessionService.endSession(workoutId, userId));
    }

    @PostMapping(urlString+"/{workout_id}/reset")
    public ResponseEntity<WorkoutSessionFullResponse> resetSession(
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(workoutSessionService.resetSession(workoutId, userId));
    }

}
