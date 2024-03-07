package com.mat.taksov.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse {
    private HttpStatus status;
    private List<String> errors;

    public ErrorResponse(HttpStatus status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
