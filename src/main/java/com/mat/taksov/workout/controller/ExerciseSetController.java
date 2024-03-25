package com.mat.taksov.workout.controller;

import com.mat.taksov.authentication.service.UserSessionService;
import com.mat.taksov.user.model.User;
import com.mat.taksov.workout.dto.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.ExerciseSetResponse;
import com.mat.taksov.workout.exception.ExerciseSetNotFoundException;
import com.mat.taksov.workout.service.ExerciseSetService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/user/{user_id}/workout/{workout_id}/exercise_set")
@RestController
@Tag(name = "ExerciseSet Management")
public class ExerciseSetController {
    private final ExerciseSetService exerciseSetService;
    private final UserSessionService userSessionService;

    @PostMapping("/create")
    public ResponseEntity<ExerciseSetResponse> createExerciseSet(
            @Valid @RequestBody ExerciseSetCreateRequest exerciseSetRequest,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @AuthenticationPrincipal User user){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(exerciseSetService.createExerciseSet(exerciseSetRequest, workoutId, userId));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ExerciseSetResponse>> getAllExerciseSets(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @PageableDefault(page = 0, size = 10, sort = {"startDate"} ) Pageable pageable
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(exerciseSetService.getAllExerciseSets(pageable));
    }

    @GetMapping("/{exercise_set_id}")
    public ResponseEntity<ExerciseSetResponse> getExerciseSetById(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @PathVariable("exercise_set_id") String exerciseSetId
    ){
        userSessionService.assertLoggedUser(user, userId);
        return ResponseEntity.ok(exerciseSetService.getExerciseSetById(exerciseSetId));
    }

    @DeleteMapping("/{exercise_set_id}/delete")
    public ResponseEntity<String> deleteExerciseSet(
            @AuthenticationPrincipal User user,
            @PathVariable("user_id") String userId,
            @PathVariable("workout_id") String workoutId,
            @PathVariable("exercise_set_id") String exerciseSetId
    ){
        userSessionService.assertLoggedUser(user, userId);
        exerciseSetService.deleteExerciseSet(exerciseSetId, userId);
        return ResponseEntity.ok("ExerciseSet borrado.");
    }
}
