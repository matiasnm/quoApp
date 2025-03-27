package com.nocountry.quo.model.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
    @NotBlank
    String username,
    @NotBlank 
    String password) {
    
}