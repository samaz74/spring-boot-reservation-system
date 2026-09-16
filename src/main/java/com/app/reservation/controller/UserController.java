package com.app.reservation.controller;

import com.app.reservation.Service.UserService;
import com.app.reservation.dto.UserResponse;
import com.app.reservation.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.findByUserId(id);
    }
    @GetMapping("/me")
    public UserResponse getCurrentUserInfo(Principal principal){
        return userService.findByEmail(principal.getName());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse changeUserAuthority(@PathVariable Long id, @RequestBody Role role){
        return userService.changeUserRole(id,role);
    }


}
