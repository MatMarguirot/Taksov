package com.mat.taksov.user.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUserRequest {
    @NotBlank
    @Length(min = 6, max = 20)
    private String username;

    @Email
    @NotBlank
    private String email;

    @Length(min = 6, max = 20)
    @NotBlank
    private String password;

    public CreateUserRequest() {}

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
