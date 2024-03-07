package com.mat.taksov.user.dto.admin;

import com.mat.taksov.authentication.model.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AdminUpdateUserDto {
    @Nullable
//    @Length(min = 6, max = 20)
    @Pattern(
            regexp = "^$|.{6,20}",
            message = "Username debe estar vacio o tener entre 6 y 20 caracteres"
    )
    private String username;

    @Nullable
    @Email
//    @Length(min = 5, max = 30)
    @Pattern(
            regexp = "^$|.{6,20}",
            message = "Email debe estar vacio o tener entre 6 y 20 caracteres"
    )
    private String email;

    @Nullable
    @Pattern(
            regexp = "^$|.{6,20}",
            message = "Password debe estar vacio o tener entre 6 y 20 caracteres"
    )
    private String password;

    @Nullable
    private Role role;

    public AdminUpdateUserDto() {}

//    // Getters
//    public String getUsername() {
//        return username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    // Setters
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }


}


