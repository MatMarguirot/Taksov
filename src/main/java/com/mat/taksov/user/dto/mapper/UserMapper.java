package com.mat.taksov.user.dto.mapper;

import com.mat.taksov.user.controller.request.CreateUserRequest;
import com.mat.taksov.user.controller.request.GetUserResponse;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;
import com.mat.taksov.user.dto.admin.AdminGetUserDto;
import com.mat.taksov.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public UserMapper(PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public User toUser(CreateUserRequest createUserRequest){
        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return modelMapper.map(createUserRequest, User.class);
    }
    public User toUser(AdminCreateUserDto createUserRequest){
        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return modelMapper.map(createUserRequest, User.class);
    }

    public GetUserResponse toGetUserResponse(User user){
        GetUserResponse res = modelMapper.map(user, GetUserResponse.class);
        res.setPassword("");
        return res;
    }
    public AdminGetUserDto toAdminGetUserDto(User user){
        user.setPassword("");
        return modelMapper.map(user, AdminGetUserDto.class);
    }

}





//@Bean
//public ModelMapper userMapper(){
//    ModelMapper modelMapper = new ModelMapper();
//
//    TypeMap<CreateUserRequest, User> createUserRequestToUserTypeMap = modelMapper.createTypeMap(CreateUserRequest.class, User.class);
//    createUserRequestToUserTypeMap.addMappings(mapping -> {
//        mapping.map(CreateUserRequest::getUsername, User::setUsername);
//        mapping.map(CreateUserRequest::getEmail, User::setEmail);
//        mapping.map(src -> src.getPassword() != null ? passwordEncoder.encode(src.getPassword()) : "", User::setPassword);
//    });
//
//    TypeMap<User, GetUserResponse> userToGetUserRequestTypeMap = modelMapper.createTypeMap(User.class, GetUserResponse.class);
//    userToGetUserRequestTypeMap.addMappings(mapping -> {
//        mapping.map(User::getUsername, GetUserResponse::setUsername);
//        mapping.map(User::getEmail, GetUserResponse::setEmail);
//        mapping.map(User::getPassword, GetUserResponse::setPassword);
//    });
//
//    return modelMapper;
//}
