package com.mat.taksov.authentication.controller;

import com.mat.taksov.authentication.dto.AuthResponse;
import com.mat.taksov.authentication.dto.LoginRequest;
import com.mat.taksov.authentication.dto.SignupRequest;
import com.mat.taksov.authentication.model.enums.RefreshToken;
import com.mat.taksov.authentication.model.enums.RefreshTokenRequestDto;
import com.mat.taksov.authentication.service.AuthService;
import com.mat.taksov.authentication.service.JwtService;
import com.mat.taksov.authentication.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthService authService;
//    private final RefreshTokenService refreshTokenService;
//    private final JwtService jwtService;
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

    @PostMapping("/refresh_token")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
//        AuthResponse res = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(user -> {
//                    String accessToken = jwtService.getToken(user);
//                    return AuthResponse.builder()
//                            .accessToken(accessToken)
//                            .accessToken(refreshTokenRequestDTO.getToken()).build();
//                }).orElseThrow(() ->new RuntimeException("No se encontró refresh token en DB"));
        return ResponseEntity.ok("ok");
    }
}
