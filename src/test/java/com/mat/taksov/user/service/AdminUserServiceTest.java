package com.mat.taksov.user.service;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.exception.EmailExistsException;
import com.mat.taksov.user.exception.UsernameExistsException;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.controller.request.GetUserResponse;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;
import com.mat.taksov.user.repository.UserRepository;
import com.mat.taksov.util.mocks.UserMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class) // allows us to use mocks
class AdminUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    // class under testing, the mocks will be injected into it
    // in this case, the mock repository will be injected to the AdminUserService
    @InjectMocks
    private AdminUserService adminUserService;

    private User createdUser;
    private AdminCreateUserDto adminCreateUserDto;
    private GetUserResponse getUserResponse;
    private List<User> userList;

    @BeforeEach
    void setUp() {
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
    void user_service_create_user_returns_user_dto() {
        // ARRANGE
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        when(                                       //  cuando en la CLASE TESTEADA (userService)
                userRepository.save(                //  se llame a userRepository.save(),
                        any(User.class)))           //  mientras sea un objeto de tipo User
                .thenReturn(createdUser);            //  retornara userCreado

        when(userMapper.map(any(AdminCreateUserDto.class), eq(User.class))).thenReturn(createdUser);
        when(userMapper.map(any(User.class), eq(GetUserResponse.class))).thenReturn(getUserResponse);

        when(passwordEncoder.encode(any(String.class))).thenReturn("$2a$10$ok1jAXQGZ/iwev.xeDaii.zQb1/jMEApWVu39djyyUhtkEFL6qDji");

        //ACT
        GetUserResponse res = adminUserService.createUser(adminCreateUserDto);

        //ASSERT
        Assertions.assertThat(res).isInstanceOf(GetUserResponse.class);
        Assertions.assertThat(res).isNotNull();
    }

//    @Test
//    void get_users_with_password_returns_user_dto_list() {
//        // Assign
////        Pageable pageable = PageRequest.of(0,10, Sort.by("username"));
//        Pageable pageable = Pageable.unpaged();
//        var users = UserMock.mockMultipleUsers(20);
//
//        // Act
//        Page<AdminGetUserDto> res = adminUserService.getUsersWithPassword(pageable);
//
//        // Assert
//        Assertions.assertThat(res.getContent()).isInstanceOf(Page.class);
//
//    }

    @Test
    void CreateUser_ThrowsEmailException_IfEmailExists (){
        // arrange
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        // act
        // assert
        Assertions.assertThatThrownBy(() -> adminUserService.createUser(adminCreateUserDto))
                .isInstanceOf(EmailExistsException.class);
    }

    @Test
    void CreateUser_ThrowsUsernameException_IfEmailExists (){
        // arrange
        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        // act
        // assert
        Assertions.assertThatThrownBy(() -> adminUserService.createUser(adminCreateUserDto))
                .isInstanceOf(UsernameExistsException.class);
    }


    @Test
    void createUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateRole() {
    }

    @Test
    void deleteUser() {
    }
}