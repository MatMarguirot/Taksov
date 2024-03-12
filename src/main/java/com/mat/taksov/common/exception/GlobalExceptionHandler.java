package com.mat.taksov.common.exception;

import com.mat.taksov.common.exception.common.BadRequestException;
import com.mat.taksov.user.exception.EmailExistsException;
import com.mat.taksov.user.exception.JwtCreationException;
import com.mat.taksov.user.exception.UserNotFoundException;
import com.mat.taksov.user.exception.UsernameExistsException;
import com.mat.taksov.workout.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(){
        log.error("ACCESS DENIED");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado.");
    }

    @ExceptionHandler(UserNotFoundException.class) //clase custom exception
    public ResponseEntity<String> handleUserNotFound(){
        log.error("USUARIO NO ENCONTRADO");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no se ha encontrado");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException e){
        log.error("ERROR DE VALIDACION");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExists(){
        log.error("NOMBRE DE USUARIO YA EXISTE");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Nombre de usuario ya existe, elija otro.");
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> handleEmailExists(){
        log.error("CORREO YA ESTA EN USO");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Este correo ya existe en la base de datos.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> methodArgumentNotValidHandler(MethodArgumentNotValidException e){
//    public ErrorResponse methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        log.error("ERROR DE VALIDACION");
        BindingResult results = e.getBindingResult();
        List<FieldError> errors = results.getFieldErrors();
        List<String> errorMessages = errors.stream().map(FieldError::getDefaultMessage).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
//        return new ErrorResponse(HttpStatusCode.valueOf(400), errorMessages);

//        StringBuilder errorMessage = new StringBuilder();
//        for(FieldError error : errors){
//            errorMessage.append(error.getDefaultMessage()).append("\n");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }

    @ExceptionHandler(InvalidBulkExerciseException.class)
    public ResponseEntity<String> handleInvalidExerciseException(){
        log.error("Carga de ejercicios ha fallado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Carga de ejercicios ha fallado.");
    }
    @ExceptionHandler(InvalidBulkMuscleGroupException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMuscleGroupException(InvalidBulkMuscleGroupException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getInsertionResults());
    }

    @ExceptionHandler(JwtCreationException.class)
    public ResponseEntity<String> handleJwtCreationException(){
        log.error("ERROR DE JWT");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear JWT");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationFailed(){
        log.error("AUTHENTICATION FAILED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
    }

    @ExceptionHandler(WorkoutDoesNotBelongException.class)
    public ResponseEntity<String> handleWorkoutDoesNotBelong(){
        log.error("El Workout no pertenece a este usuario.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El Workout no pertenece a este usuario.");
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    public ResponseEntity<String> handleWorkoutNotFound(){
        log.error("No se ha encontrado el Workout.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el Workout.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(){
        log.error("Sesión ha expirado.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sesión ha expirado.");
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<String> handleExerciseNotFoundException(){
        log.error("No se ha encontrado el ejercicio.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se ha encontrado el ejercicio.");
    }
    @ExceptionHandler(ExerciseSetNotFoundException.class)
    public ResponseEntity<String> handleExerciseSetNotFoundException(){
        log.error("No se ha encontrado el set.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se ha encontrado el set.");
    }

    @ExceptionHandler(MuscleGroupNotFoundException.class)
    public ResponseEntity<String> handleMuscleGroupNotFoundException(MuscleGroupNotFoundException e){
        log.error("No se ha encontrado el ejercicio.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se ha encontrado el ejercicio.");
    }

    @ExceptionHandler(WorkoutIllegalStateException.class)
    public ResponseEntity<String> handleWorkoutSessionIllegalStateException(WorkoutIllegalStateException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoWorkoutsForUserException.class)
    public ResponseEntity<String> handleNoWorkoutsForUserException(){
        log.error("No se ha encontrado sesiones de entrenamiento para este usuario.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado sesiones de entrenamiento para este usuario.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<String> handleDataAccessException()
}
