package com.mat.taksov.workout.exception;

public class NoWorkoutsForUserException extends RuntimeException{
    public NoWorkoutsForUserException() {
    }

    public NoWorkoutsForUserException(String message) {
        super(message);
    }

    public NoWorkoutsForUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoWorkoutsForUserException(Throwable cause) {
        super(cause);
    }

    public NoWorkoutsForUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
