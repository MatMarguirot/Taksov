package com.mat.taksov.workout.exception;

public class WorkoutDoesNotBelongException extends RuntimeException{
    public WorkoutDoesNotBelongException() {
    }

    public WorkoutDoesNotBelongException(String message) {
        super(message);
    }

    public WorkoutDoesNotBelongException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkoutDoesNotBelongException(Throwable cause) {
        super(cause);
    }

    public WorkoutDoesNotBelongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
