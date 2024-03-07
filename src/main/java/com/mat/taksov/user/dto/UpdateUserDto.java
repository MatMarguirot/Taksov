package com.mat.taksov.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class UpdateUserDto {
    @NotBlank
    @Length(min = 6, max = 20)
    private String username;

    @Email
    @NotBlank
    private String email;

    @Length(min = 6, max = 20)
    private String password;
}
