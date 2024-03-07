package com.mat.taksov.authentication.service;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.common.controller.AbstractLogged;
import com.mat.taksov.user.exception.UserNotFoundException;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserSessionService extends AbstractLogged {
    private final UserRepository userRepository;
    public void assertLoggedUser(User user, String user_id){
        if(user.getRole().equals(Role.ADMIN)) {
            if(!userRepository.existsById(user_id)) throw new UserNotFoundException();
            return;
        }

        logger.debug("current user id : "+user.getId());
        logger.debug("entered id: "+user_id);

        if(!user.getId().equals(user_id)){
            logger.debug("Usuario no tiene permisos");
            throw new RuntimeException();
        }
        logger.debug("Usuario tiene permiso");
    }
}
