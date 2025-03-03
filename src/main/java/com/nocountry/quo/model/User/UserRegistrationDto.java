package com.nocountry.quo.model.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(

        @NotNull(message = "El nombre de usuario no puede ser nulo")
        @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
        String username,

        @NotNull(message = "La contraseña no puede ser nula")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotNull(message = "El correo electrónico no puede ser nulo")
        @Email(message = "Debe ser un correo electrónico válido")
        String mail,

        String phone, // El teléfono puede ser opcional

        String avatar // El avatar también puede ser opcional
) {}
