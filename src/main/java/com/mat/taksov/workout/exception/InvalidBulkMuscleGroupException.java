package com.mat.taksov.workout.exception;

import java.util.Map;

public class InvalidBulkMuscleGroupException extends RuntimeException{
    private Map<String, String> insertionResults;
    public InvalidBulkMuscleGroupException() {
    }
    public InvalidBulkMuscleGroupException(Map<String, String> insertionResults) {
        this.insertionResults = insertionResults;
    }

    public Map<String, String> getInsertionResults() {
        return insertionResults;
    }

    public InvalidBulkMuscleGroupException(String message) {
        super(message);
    }

    public InvalidBulkMuscleGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBulkMuscleGroupException(Throwable cause) {
        super(cause);
    }

    public InvalidBulkMuscleGroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
