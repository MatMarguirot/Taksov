package com.mat.taksov.user.dto.admin;

import com.mat.taksov.authentication.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
//import org.mapstruct.Mapping;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetUserDto implements GetUserDtoInterface {
    @NotBlank
    private String id;

    @NotBlank
    @Length(min = 6, max = 20)
    private String username;

    @Email
    @NotBlank
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}


//    public GetUserDTO(String id, String username, String email) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//    }
//}
