package com.mat.taksov.user.controller.request;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.user.dto.admin.GetUserDtoInterface;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
//import org.mapstruct.Mapping;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse implements GetUserDtoInterface {
    @NotBlank
    private String id;

    @NotBlank
    @Length(min = 6, max = 20)
    private String username;

    @Length(min = 6, max = 20)
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

//    public GetUserDto() {
//    }
}


//    public GetUserDTO(String id, String username, String email) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//    }
//}
