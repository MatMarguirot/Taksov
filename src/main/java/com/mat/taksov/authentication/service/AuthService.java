package com.mat.taksov.authentication.service;


import com.mat.taksov.authentication.dto.AuthResponse;
import com.mat.taksov.authentication.dto.LoginRequest;
import com.mat.taksov.authentication.dto.SignupRequest;
import com.mat.taksov.user.model.User;
import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.common.exception.common.BadRequestException;
import com.mat.taksov.common.exception.common.DataIntegrityViolationException;
import com.mat.taksov.user.exception.EmailExistsException;
import com.mat.taksov.user.exception.JwtCreationException;
import com.mat.taksov.user.exception.UsernameExistsException;
import com.mat.taksov.user.repository.UserRepository;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Log
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final ModelMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthResponse signup(SignupRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistsException();
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailExistsException();
        }

        // persistencia
        try{
            User newUser = userMapper.map(request, User.class);
            if(userRepository.count() < 1) {
                newUser.setRole(Role.ADMIN);
            }else{
                newUser.setRole(Role.USER);
            }
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            String newUserId = userRepository.save(newUser).getId();
            AuthResponse res = AuthResponse.builder()
                    .token(jwtService.getToken(newUser))
//                    .id(newUserId)
                    .build();
            return res;
        }catch(RuntimeException e){
            logger.error("Error " + e);
            if(e instanceof DataIntegrityViolationException){
                throw new DataIntegrityViolationException();
            }
            if(e instanceof WeakKeyException){
                throw new JwtCreationException();
            }
            throw new BadRequestException();
        }
    }

}


// antes de cambio id

//public AuthResponse login(LoginRequest request){
//    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//    // obtiene UserDetails para crear el JWT
//    UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
//    String token = jwtService.getToken(user);
//    return AuthResponse.builder()
//            .token(token)
//            .build();
//}
//
//public AuthResponse signup(SignupRequest request){
//    if(userRepository.existsByUsername(request.getUsername())){
//        throw new UsernameExistsException();
//    }
//    if(userRepository.existsByEmail(request.getEmail())){
//        throw new EmailExistsException();
//    }
//
//    // persistencia
//    try{
//        User newUser = userMapper.map(request, User.class);
//        if(userRepository.count() < 1) {
//            newUser.setRole(Role.ADMIN);
//        }else{
//            newUser.setRole(Role.USER);
//        }
//        newUser.setPassword(passwordEncoder.encode(request.getPassword())); //pasar esto al mapper
//        AuthResponse res = AuthResponse.builder()
//                .token(jwtService.getToken(newUser))
//                .build();
//        userRepository.save(newUser);
//        return res;
//    }catch(RuntimeException e){
//        logger.error("Error " + e);
//        if(e instanceof DataIntegrityViolationException){
//            throw new DataIntegrityViolationException();
//        }
//        if(e instanceof WeakKeyException){
//            throw new JwtCreationException();
//        }
//        throw new BadRequestException();
//    }
//}