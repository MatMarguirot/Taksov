package com.mat.taksov.common.exception.common;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String message) {
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegrityViolationException(Throwable cause) {
        super(cause);
    }

    public DataIntegrityViolationException() {
    }
}
