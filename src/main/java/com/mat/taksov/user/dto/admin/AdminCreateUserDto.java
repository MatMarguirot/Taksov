package com.mat.taksov.user.dto.admin;

import com.mat.taksov.authentication.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder // para tests, que importa que sea mutable
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateUserDto {
    @NotBlank
    @Length(min = 6, max = 20)
    private String username;

    @Email
    @NotBlank
    private String email;

    @Length(min = 6, max = 20)
    @NotBlank
    private String password;

    @NotNull
    private Role role;


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
