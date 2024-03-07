package com.mat.taksov.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateUserPasswordDto {
    @Length(min = 6, max = 20)
    @NotBlank
    @NotNull
    private String password;
}
