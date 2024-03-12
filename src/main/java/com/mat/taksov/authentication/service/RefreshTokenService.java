package com.mat.taksov.authentication.service;

import com.mat.taksov.authentication.model.enums.RefreshToken;
import com.mat.taksov.authentication.repository.RefreshTokenRepository;
import com.mat.taksov.user.exception.UserNotFoundException;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        RefreshToken foundToken = refreshTokenRepository.findByUserId(user.getId()).orElse(null);
        if(foundToken != null) {
            verifyExpiration(foundToken);
            return foundToken;
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000*60*24*10))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(rollbackFor = Exception.class)
    public RefreshToken createRefreshTokenForNewUser(User user){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000*60*24*10))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}