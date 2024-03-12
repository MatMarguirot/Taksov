package com.mat.taksov.workout.exception;

public class InvalidBulkWorkoutException extends RuntimeException{
    public InvalidBulkWorkoutException() {
    }

    public InvalidBulkWorkoutException(String message) {
        super(message);
    }

    public InvalidBulkWorkoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBulkWorkoutException(Throwable cause) {
        super(cause);
    }

    public InvalidBulkWorkoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
