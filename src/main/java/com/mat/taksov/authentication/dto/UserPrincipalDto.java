package com.mat.taksov.authentication.dto;

import com.mat.taksov.authentication.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//projection
@AllArgsConstructor
@Getter
@Setter
public class UserPrincipalDto {
    private String id;
    private String username;
    private Role role;
    private String password;


}
