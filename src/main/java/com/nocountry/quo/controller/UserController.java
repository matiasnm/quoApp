package com.nocountry.quo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.model.User.UserRegistrationDto;
import com.nocountry.quo.service.UserRegistrationService;
import com.nocountry.quo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService; // Asegúrate de tener el UserService para manipular los usuarios

    // Endpoint de registro de usuario
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto registrationDto) {

        // Usar el servicio de registro para registrar al nuevo usuario
        UserResponseDto userResponse = userRegistrationService.registerNewUser(
                registrationDto.username(),
                registrationDto.password(),
                registrationDto.mail(),
                registrationDto.phone(),
                registrationDto.avatar()
        );

        // Retornar una respuesta con código 200 OK y el DTO del usuario registrado
        return ResponseEntity.ok(userResponse);
    }

    // Obtener los detalles del usuario
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.read(id, userDetails));
    }

    // Actualizar el nombre y teléfono del usuario
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestBody User user,
            @AuthenticationPrincipal User loggedInUser) {
        return ResponseEntity.ok(userService.updateUserInfo(
                loggedInUser.getId(), user.getUsername(), user.getPhone()));
    }

    // Actualizar el avatar del usuario
    @PutMapping("/update-avatar")
    public ResponseEntity<User> updateAvatar(
            @RequestParam String avatar,
            @AuthenticationPrincipal User loggedInUser) {
        return ResponseEntity.ok(userService.updateAvatar(loggedInUser.getId(), avatar));
    }

    // Modificar la cuenta del usuario para marcarla como inactiva
    @PutMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@AuthenticationPrincipal User loggedInUser) {
        // Llamar al servicio para desactivar la cuenta del usuario
        userService.deactivateAccount(loggedInUser.getId());
        return ResponseEntity.ok().build(); // Retorna 200 OK sin contenido
    }
}
