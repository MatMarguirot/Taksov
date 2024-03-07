package com.mat.taksov.user.exception;

public class JwtCreationException extends RuntimeException{
    public JwtCreationException() {
    }

    public JwtCreationException(String message) {
        super(message);
    }

    public JwtCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtCreationException(Throwable cause) {
        super(cause);
    }

    public JwtCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
