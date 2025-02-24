package com.nocountry.quo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.read(id));
    }

}