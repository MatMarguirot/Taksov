package com.mat.taksov.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Pattern(
            regexp = "^$|.{6,20}",
            message = "Username debe estar vacio o tener entre 6 y 20 caracteres"
    )
    String username;
//    @Email
//    String email;
    @NotBlank
    @Length(min = 6, max = 20, message = "Contraseña debe tener entre 6 y 20 ")
    String password;
}
