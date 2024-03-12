package com.mat.taksov.workout.exception;

public class InvalidBulkExerciseException extends RuntimeException{
    public InvalidBulkExerciseException() {
    }

    public InvalidBulkExerciseException(String message) {
        super(message);
    }

    public InvalidBulkExerciseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBulkExerciseException(Throwable cause) {
        super(cause);
    }

    public InvalidBulkExerciseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
