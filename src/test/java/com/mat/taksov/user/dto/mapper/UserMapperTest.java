package com.mat.taksov.user.dto.mapper;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.controller.request.GetUserResponse;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;
import com.mat.taksov.user.model.User;
import com.mat.taksov.util.mocks.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User createdUser;
    private AdminCreateUserDto adminCreateUserDto;
    private GetUserResponse getUserResponse;
    private List<User> userList;

    @BeforeEach
    void setUp(){
        // sets up everything  before each test
        createdUser = new User();
        createdUser.setUsername("User5");
        createdUser.setPassword("Electric");
        createdUser.setEmail("testUser5@gmail.com");
        createdUser.setRole(Role.USER);

        adminCreateUserDto = AdminCreateUserDto.builder()
                .username("User5")
                .password("Electric")
                .email("testUser5@gmail.com")
                .role(Role.USER)
                .build();

        getUserResponse = GetUserResponse.builder()
                .username("User5")
//                .password("Electric")
                .email("testUser5@gmail.com")
                .role(Role.USER)
                .build();

        userList = UserMock.mockMultipleUsers(5);
    }

    @Test
    void Converts_To_Dto_And_Encrypts_Password(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = adminCreateUserDto.getPassword();
        User user = userMapper.toUser(adminCreateUserDto);

        assertAll(
                () -> assertEquals(user.getUsername(), adminCreateUserDto.getUsername()),
                () -> assertEquals(user.getEmail(), adminCreateUserDto.getEmail()),
                () -> assertEquals(user.getRole(), adminCreateUserDto.getRole()),
                () -> assertTrue(encoder.matches(password, user.getPassword()))
        );
    }
}