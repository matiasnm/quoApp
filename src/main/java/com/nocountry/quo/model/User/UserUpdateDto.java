package com.nocountry.quo.model.User;

import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(
    @NotNull
    String username,
    @NotNull
    String mail,
    @NotNull
    String phone
) {}