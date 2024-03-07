package com.mat.taksov.user.controller;

import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.common.controller.AbstractLogged;
import com.mat.taksov.user.controller.request.GetUserResponse;
import com.mat.taksov.user.dto.UpdateUserEmailDto;
import com.mat.taksov.user.dto.UpdateUserRoleDto;
import com.mat.taksov.user.dto.UpdateUserUsernameDto;
import com.mat.taksov.user.dto.admin.AdminCreateUserDto;
import com.mat.taksov.user.dto.admin.AdminUpdateUserDto;
import com.mat.taksov.user.dto.admin.GetUserDtoInterface;
import com.mat.taksov.user.model.User;
import com.mat.taksov.user.service.AdminUserService;
//import com.mat.taksov.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@AllArgsConstructor
public class AdminUserController extends AbstractLogged {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<Page<? extends GetUserDtoInterface>> getUsers(
            Pageable pageable,
            @RequestParam(name = "with_pwd", defaultValue = "false", required = false) boolean withPwd
    ){
        if(withPwd) {
            return ResponseEntity.ok(adminUserService.getUsersWithPassword(pageable));
        }
        return ResponseEntity.ok(adminUserService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable String id){
        return ResponseEntity.ok(adminUserService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<GetUserResponse> createUser(@Valid @RequestBody AdminCreateUserDto createUserDTO){
        return ResponseEntity.ok(adminUserService.createUser(createUserDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody AdminUpdateUserDto updateUserDto){
        adminUserService.updateUser(id, updateUserDto);
        String res = "Se ha actualizado el usuario "+id;
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{user_id}/username")
    public ResponseEntity<String> updateUserUsername(
            @PathVariable("user_id") String id,
            @Valid @RequestBody UpdateUserUsernameDto updateUserUsernameDto
            ){
        adminUserService.updateUsername(id, updateUserUsernameDto);
        String res = "Se ha cambiado el username del usuario "+id+". Nuevo username: "+updateUserUsernameDto.getUsername();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<String> updateUserEmail(@PathVariable String id, @Valid @RequestBody UpdateUserEmailDto updateUserEmailDto){
        adminUserService.updateEmail(id, updateUserEmailDto.getEmail());
        String res = "Se ha cambiado el email del usuario "+id+". Nuevo email: "+updateUserEmailDto.getEmail();
        return  ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable String id, @RequestBody String password){
        adminUserService.updatePassword(id, password);
        String res = "Se ha cambiado la contraseña del usuario "+id+". Nueva contraseña: "+password;
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable String id, @Valid @RequestBody UpdateUserRoleDto updateUserRoleDto){
        Role role = updateUserRoleDto.getRole();
        adminUserService.updateRole(id, role);
        String res = "Se ha cambiado el rol del usuario "+id+". Nuevo rol: "+role;
        return ResponseEntity.ok(res);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        return ResponseEntity.ok(adminUserService.deleteUser(id));
    }

}
