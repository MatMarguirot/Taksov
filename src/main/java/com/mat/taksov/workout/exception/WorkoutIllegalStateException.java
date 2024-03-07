package com.mat.taksov.workout.exception;

public class WorkoutIllegalStateException extends RuntimeException {
    public WorkoutIllegalStateException() {
    }

    public WorkoutIllegalStateException(String message) {
        super(message);
    }

    public WorkoutIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkoutIllegalStateException(Throwable cause) {
        super(cause);
    }

    public WorkoutIllegalStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
