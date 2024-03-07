package com.mat.taksov.user.dto;

import com.mat.taksov.authentication.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleDto {
    @NotBlank
    @NotNull
    private Role role;
}
