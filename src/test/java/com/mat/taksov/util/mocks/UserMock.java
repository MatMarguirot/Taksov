package com.mat.taksov.util.mocks;

import com.mat.taksov.user.model.User;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMock {
    private UserMock(){}

    public static User mockUser(){
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username("User")
                .email("user@gmail.com")
                .password("$2a$10$ok1jAXQGZ/iwev.xeDaii.zQb1/jMEApWVu39djyyUhtkEFL6qDji")
                .build();
    }

    public static User mockUser(String id){
        return User.builder()
                .id(id)
                .username("User")
                .email("user@gmail.com")
                .password("$2a$10$ok1jAXQGZ/iwev.xeDaii.zQb1/jMEApWVu39djyyUhtkEFL6qDji")
                .build();
    }
    public static User mockUser(AdminCreateUserDto dto){
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static List<User> mockMultipleUsers(int count){
        List<User> users = new ArrayList<>();
        for(int i = 0; i < count; i++){
            users.add(mockUser());
        }
        return users;
    }
}
