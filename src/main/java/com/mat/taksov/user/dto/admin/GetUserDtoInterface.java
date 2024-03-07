package com.mat.taksov.user.dto.admin;
import com.mat.taksov.authentication.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

public interface GetUserDtoInterface {

//    @NotBlank
//    Optional<String> getId();
//
//    @NotBlank
//    @Length(min =  6, max =  20)
//    Optional<String> getUsername();
//
//    @Email
//    @NotBlank
//    Optional<String> getEmail();
//
//    Optional<Role> getRole();
}