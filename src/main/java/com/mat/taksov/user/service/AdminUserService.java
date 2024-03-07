package com.mat.taksov.user.service;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.common.controller.AbstractLogged;
import com.mat.taksov.common.exception.common.BadRequestException;
import com.mat.taksov.common.exception.common.DataIntegrityViolationException;
import com.mat.taksov.user.dto.mapper.UserMapper;
import com.mat.taksov.user.exception.EmailExistsException;
import com.mat.taksov.user.exception.UserNotFoundException;
import com.mat.taksov.user.exception.UsernameExistsException;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.dto.UpdateUserUsernameDto;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;
import com.mat.taksov.user.controller.request.GetUserResponse;
import com.mat.taksov.user.dto.admin.AdminGetUserDto;
import com.mat.taksov.user.dto.admin.AdminUpdateUserDto;
import com.mat.taksov.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService extends AbstractLogged {
    private final UserRepository userRepository;
//    private final ModelMapper userMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getUsersTEST(Pageable pageable){
        List<User> users = userRepository.findAll();
        return users;
//        return users.map((user) -> userMapper.map(user, GetUserResponse.class));
    }

    @Transactional(readOnly = true)
    public Page<GetUserResponse> getUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toGetUserResponse);
//        return users.map((user) -> userMapper.map(user, GetUserResponse.class));
    }
    @Transactional(readOnly = true)
    public Page<AdminGetUserDto> getUsersWithPassword(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        Page<AdminGetUserDto> page = users.map(userMapper::toAdminGetUserDto);
        return page;
//    return users.map((user) -> userMapper.map(user, AdminGetUserDto .class));
    }

    @Transactional(rollbackFor = Exception.class)
    public GetUserResponse createUser(AdminCreateUserDto adminCreateUserDto){
        if(userRepository.existsByUsername(adminCreateUserDto.getUsername())) throw new UsernameExistsException();
        if(userRepository.existsByEmail(adminCreateUserDto.getEmail())) throw new EmailExistsException();
        try{
            User newUser = userMapper.toUser(adminCreateUserDto);
//            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            User createdUser = userRepository.save(newUser);
            GetUserResponse res = userMapper.toGetUserResponse(newUser);
            return res;
        }catch(RuntimeException e){
            logger.error("Error " + e);
            if(e instanceof DataIntegrityViolationException){
                throw new DataIntegrityViolationException();
            }
            throw new BadRequestException();
        }
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUserById(String id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        return userMapper.toGetUserResponse(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateUser(String id, AdminUpdateUserDto updateUserDto) {
            User user = userRepository.findById(id).orElseThrow(() -> {
                logger.debug("Usuario no encontrado.");
                return new UserNotFoundException("UserNotFound");
            });
            String email = updateUserDto.getEmail();
            String username = updateUserDto.getUsername();
            String password = updateUserDto.getPassword();
            Role role = updateUserDto.getRole();

            if(username != null && !username.isBlank()){
                if(!username.equals(user.getUsername()) && userRepository.existsByUsername(username)) throw new UsernameExistsException();
                logger.debug("Nuevo username: " + username + ". Antiguo username: "+ user.getUsername() + ". Son iguales: " + String.valueOf(username.equals(user.getUsername())));
                user.setUsername(username);
            }
            if(email != null && !email.isBlank()){
                if(!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) throw new EmailExistsException();
                logger.debug("Nuevo email: " + email +". Antiguo email: " + user.getEmail() + ". Son iguales: " + String.valueOf(email.equals(user.getEmail())));
                user.setEmail(email);
            }
            if(password != null && !password.isBlank()){
                if(!passwordEncoder.matches(password, user.getPassword())){
                    logger.debug("Nuevo password: " + password + ". Antiguo password: "+ user.getPassword() + ". Son iguales: " + String.valueOf(password.equals(user.getPassword())));
                    user.setPassword(passwordEncoder.encode(password));
                }
            }
            if(role != null){
                logger.debug("Nuevo rol: " + role + ". Antiguo rol: "+ user.getRole());
                user.setRole(role);
            }

            return userRepository.save(user).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateUsername(String id, UpdateUserUsernameDto updateUserUsernameDto){
        if(!userRepository.existsById(id)) throw new UserNotFoundException();
        if(userRepository.existsByUsername(updateUserUsernameDto.getUsername())) throw new UsernameExistsException();
        userRepository.updateUserUsername(id, updateUserUsernameDto.getUsername());
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateEmail(String id, String email){
        if(!userRepository.existsById(id)) throw new UserNotFoundException();
        if(userRepository.existsByEmail(email)) throw new EmailExistsException();
        userRepository.updateUserEmail(id, email);
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updatePassword(String id, String password){
        if(!userRepository.existsById(id)) throw new UserNotFoundException();
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        logger.debug("Cambiando password de usuario "+user.getUsername());
        logger.debug("Password en db: "+user.getPassword());
        if(!passwordEncoder.matches(password, user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(password);
            logger.debug("Password encriptada: "+encodedPassword);
            userRepository.updateUserPassword(id, passwordEncoder.encode(password));
        }
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateRole(String id, Role role){
        if(!userRepository.existsById(id)) throw new UserNotFoundException();
        userRepository.updateUserRole(id, role);
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    logger.debug("Usuario no encontrado");
                    throw new UserNotFoundException("Error al borrar usuario con id: "+id);
                }
        );
        userRepository.delete(user);
        return "Se borró usuario con id: " + id;
    }


    //    @Transactional(readOnly = true)
//    public List<GetUserDto> getAllUsers(){
//        List<User> userList = userRepository.findAll();
//        List<GetUserDto> getUserDtoList = userMapper.map(userList, new TypeToken<List<GetUserDto>>(){}.getType());
//        return getUserDtoList;
//    }
//    @Transactional(readOnly = true)
//    public List<GetUserDto> getUsers(Pageable pageable){
//        Page<User> users = userRepository.findAll(pageable);
//        List<GetUserDto> getUserDtoList = users.stream()
//                .map( (user) -> userMapper.map(user, GetUserDto.class) )
//                .toList();
//        return getUserDtoList;
//    }
}

