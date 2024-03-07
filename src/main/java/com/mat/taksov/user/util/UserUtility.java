package com.mat.taksov.user.util;

import com.mat.taksov.user.exception.UsernameExistsException;
import com.mat.taksov.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtility {
    private final UserRepository userRepository;


//    public boolean existsByUsername(String username){
//        return userRepository.existsByUsername(username);
//    }
//
//    public boolean existsByEmail(String email){
//        return userRepository.existsByEmail(email);
//    }
//
//    public boolean existsByUsernameOrEmail(String username, String email){
//        return (existsByUsername(username) || existsByEmail(email));
//    }
}
