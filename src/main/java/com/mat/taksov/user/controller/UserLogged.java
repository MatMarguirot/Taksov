//package com.mat.taksov.user.controller;
//import com.mat.taksov.common.controller.AbstractLogged;
//import com.mat.taksov.user.model.dto.GetUserDto;
//import com.mat.taksov.user.model.dto.CreateUserDto;
//import com.mat.taksov.user.model.dto.UpdateUserDto;
//import com.mat.taksov.user.model.User;
//import com.mat.taksov.user.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/users")
////@Log
//public class UserLogged extends AbstractLogged {
//    private final UserService userService;
//
//    public UserLogged(UserService userService){
//        this.userService = userService;
//    };
//
//    @PostMapping
//    public ResponseEntity<GetUserDto> addUser(@Valid @RequestBody CreateUserDto createUserDTO){
//        return userService.createUser(createUserDTO);
//    };
//
//    @GetMapping
//    public List<GetUserDto> getAllUsers(){
//        logger.debug("ENTRANDO A GET USERS");
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<GetUserDto> getUserById(@Valid @PathVariable String id) {
//        GetUserDto foundUser = userService.getUserById(id);
//        logger.debug(foundUser.toString());
//        return new ResponseEntity<>(foundUser, HttpStatus.OK);
//    }
//
//    @Valid
//    @PutMapping("/{id}")
//    public User updateUser(@Valid @PathVariable String id, @Valid @RequestBody UpdateUserDto userDetails) {
//        return userService.updateUser(id, userDetails);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable String id) throws Exception {
//        return userService.deleteUser(id);
//    }
//}
