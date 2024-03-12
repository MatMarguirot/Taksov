package com.mat.taksov.authentication.service;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.exception.UserNotFoundException;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class UserSessionService {
    private final UserRepository userRepository;
    public void assertLoggedUser(User user, String user_id){
        if(user.getRole().equals(Role.ADMIN)) {
            if(!userRepository.existsById(user_id)) throw new UserNotFoundException();
            return;
        }

        log.debug("current user id : "+user.getId());
        log.debug("entered id: "+user_id);

        if(!user.getId().equals(user_id)){
            log.debug("Usuario no tiene permisos");
            throw new RuntimeException();
        }
        log.debug("Usuario tiene permiso");
    }
}
