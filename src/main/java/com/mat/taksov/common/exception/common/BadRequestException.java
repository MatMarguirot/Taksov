package com.mat.taksov.common.exception.common;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException() {
    }


}
