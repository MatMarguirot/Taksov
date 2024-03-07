//package com.mat.taksov.user.service;
//
//import com.mat.taksov.common.controller.AbstractLogged;
//import com.mat.taksov.user.model.dto.CreateUserDto;
//import com.mat.taksov.user.model.dto.GetUserDto;
////import com.mat.taksov.domain.user.dto.mapper.UserMapper;
//import com.mat.taksov.user.model.dto.UpdateUserDto;
//import com.mat.taksov.user.model.User;
//import com.mat.taksov.common.exception.common.BadRequestException;
//import com.mat.taksov.common.exception.common.DataIntegrityViolationException;
//import com.mat.taksov.user.exception.EmailExistsException;
//import com.mat.taksov.user.exception.UserNotFoundException;
//import com.mat.taksov.user.exception.UsernameExistsException;
//import com.mat.taksov.user.repository.UserRepository;
//import lombok.extern.java.Log;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Log
//public class UserService extends AbstractLogged {
//
////    private final Logger logger = LoggerFactory.getLogger(UserService.class);
//    private final UserRepository userRepository;
//    private final ModelMapper userMapper;
//
//
//
////    @Autowired // necessary only for third party
//    public UserService(UserRepository userRepository, ModelMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }
//
//    public ResponseEntity<GetUserDto> createUser(CreateUserDto createUserDto){
//        // validacion de datos preexistentes
//        if(userRepository.existsByUsername(createUserDto.getUsername())){
//            throw new UsernameExistsException();
//        }
//        if(userRepository.existsByEmail(createUserDto.getEmail())){
//            throw new EmailExistsException();
//        }
//
//
//        // persistencia
//        try{
//            User newUser = userMapper.map(createUserDto, User.class);
//            User createdUser = userRepository.save(newUser);
//            GetUserDto userResponse = userMapper.map(createdUser, GetUserDto.class);
//            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
//        }catch(RuntimeException e){
//            logger.error("Error " + e);
//            if(e instanceof DataIntegrityViolationException){
//                throw new DataIntegrityViolationException();
//            }
//            throw new BadRequestException();
//        }
//    }
//
////    public List<User> getAllUsers(){
////        return userRepository.findAll();
////    }
//    public List<GetUserDto> getAllUsers(){
//        List<User> userList = userRepository.findAll();
//        List<GetUserDto> getUserDtoList = userMapper.map(userList, new TypeToken<List<GetUserDto>>(){}.getType());
//        return getUserDtoList;
//    }
//
//    public GetUserDto getUserById(String id){
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UserNotFoundException("User not found")
//        );
//        return userMapper.map(user, GetUserDto.class);
//    }
//
//    public User getUserByUsername(String username){
//        return userRepository.findByUsername(username).orElseThrow(
//                () -> new UserNotFoundException()
//        );
//    }
//
//    public User updateUser(String id, UpdateUserDto userDetails) {
//        try{
//            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("UserNotFound"));
//
////            WHAT GOES HERE?
//
//            return userRepository.save(user);
//        }catch(Exception e){
//            logger.error("Problema en updateUser", e);
//            throw new UserNotFoundException("Problema al actualizar usuario con id: " + id);
//        }
//
//
//    }
//
//    public String deleteUser(String id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error al borrar usuario con id: "+id));
//        userRepository.delete(user);
//        return "Se borró usuario con id: " + id;
//    }
//
//}
