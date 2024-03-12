package com.mat.taksov.common.exception;

import jakarta.servlet.ServletResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@Getter
public class ErrorResponse {
//public class ErrorResponse implements ServletResponse {
    private HttpStatusCode status;
    private List<String> errors;

    public ErrorResponse(HttpStatusCode status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
    public ErrorResponse(HttpStatusCode status, String error) {
        this.errors.add(error);
        this.status = status;
//        this.errors = errors;
    }
}
