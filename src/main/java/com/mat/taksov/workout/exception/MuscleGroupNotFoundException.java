package com.mat.taksov.workout.exception;

public class MuscleGroupNotFoundException extends RuntimeException{
    public MuscleGroupNotFoundException() {
    }

    public MuscleGroupNotFoundException(String message) {
        super(message);
    }

    public MuscleGroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MuscleGroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public MuscleGroupNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
