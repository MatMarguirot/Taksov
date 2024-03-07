package com.mat.taksov.user.repository;

import com.mat.taksov.authentication.dto.UserPrincipalDto;
import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<UserPrincipalDto> findUserPrincipalByUsername(String username);
    public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    @Modifying
    @Query("update User u set u.username = :username where u.id = :id")
    public void updateUserUsername(@Param("id") String id, @Param("username") String username);
    @Modifying
    @Query("update User u set u.email = :email where u.id = :id")
    public void updateUserEmail(@Param("id") String id, @Param("email") String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    public void updateUserPassword(@Param("id") String id, @Param("password") String password);

    @Modifying
    @Query("update User u set u.role = :role where u.id = :id")
    public void updateUserRole(@Param("id") String id, @Param("role") Role role);
}
