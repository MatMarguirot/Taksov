package com.mat.taksov.user.repository;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void user_repository_save_all_returns_saved_user() {
        // ARRANGE
        User user = User.builder()
                .username("User5")
                .password("Electric")
                .email("testUser5@gmail.com")
                .role(Role.USER)
                .build();

        //ACT
        User savedUser = userRepository.save(user);

        //ASSERT
        Assertions.assertThat(savedUser).isNotNull();
    }
    @Test
    void findByUsername() {

    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void updateUserUsername() {
    }

    @Test
    void updateUserEmail() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void updateUserRole() {
    }
}