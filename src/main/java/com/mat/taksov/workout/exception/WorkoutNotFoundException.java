package com.mat.taksov.workout.exception;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException() {
    }

    public WorkoutNotFoundException(String message) {
        super(message);
    }

    public WorkoutNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkoutNotFoundException(Throwable cause) {
        super(cause);
    }

    public WorkoutNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
