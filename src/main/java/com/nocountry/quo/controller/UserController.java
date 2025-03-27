package com.nocountry.quo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.model.User.UserUpdateDto;
import com.nocountry.quo.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> get(
        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.read(userDetails));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody @Valid UserUpdateDto userDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateInfo(userDetails, userDto));
    }

    @PutMapping("/update-avatar")
    public ResponseEntity<UserResponseDto> updateAvatar(
            @RequestParam String avatar,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.updateAvatar(userDetails, avatar));
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deactivateAccount(userDetails);
        return ResponseEntity.noContent().build();
    }
}
