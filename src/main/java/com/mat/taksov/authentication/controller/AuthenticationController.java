package com.mat.taksov.authentication.controller;

import com.mat.taksov.authentication.dto.AuthResponse;
import com.mat.taksov.authentication.dto.LoginRequest;
import com.mat.taksov.authentication.dto.SignupRequest;
import com.mat.taksov.authentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request
    ){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid SignupRequest request
            ){
        return ResponseEntity.ok(authService.signup(request));
    }
}
