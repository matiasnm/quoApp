package com.nocountry.quo.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(
    @NotNull
    String username,
    @Email
    @NotNull
    String mail,
    @NotNull
    String phone
) {}