package com.mat.taksov.workout.exception;

public class ExerciseSetNotFoundException extends RuntimeException{
    public ExerciseSetNotFoundException() {
    }

    public ExerciseSetNotFoundException(String message) {
        super(message);
    }

    public ExerciseSetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExerciseSetNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExerciseSetNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
