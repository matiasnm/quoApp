package com.nocountry.quo.service;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.model.User.UserUpdateDto;
import com.nocountry.quo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto read(Long id, UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();

        // Validar si el usuario que hace la solicitud es el mismo que el del ID proporcionado
        if (id != userId) {
            throw new AccessDeniedException("Unauthorized.");
        }

        Optional<User> userOptional = userRepository.findById(id);

        // Comprobar si el usuario existe en la base de datos
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with ID=" + id + " not found.");
        }

        User user = userOptional.get();
        return new UserResponseDto(user);  // Retornar el DTO con los datos del usuario
    }


    // Método para actualizar el nombre, mail y teléfono del usuario
    @Transactional
    public UserResponseDto updateInfo(UserDetails userDetails, UserUpdateDto userDto) {

        Long userId = ((User) userDetails).getId();

        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar los campos de nombre y teléfono
        user.setUsername(userDto.username());
        user.setPhone(userDto.phone());
        user.setMail(userDto.mail());

        // Guardar los cambios en la base de datos
        return new UserResponseDto(userRepository.save(user));
    }

    // Método para actualizar el avatar del usuario
    @Transactional
    public UserResponseDto updateAvatar(UserDetails userDetails, String avatar) {

        Long userId = ((User) userDetails).getId();

        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el campo del avatar
        user.setAvatar(avatar);

        // Guardar los cambios en la base de datos
        return new UserResponseDto(userRepository.save(user));
    }

    // Método para eliminar la cuenta del usuario
    @Transactional
    public void deactivateAccount(UserDetails userDetails) {

        Long userId = ((User) userDetails).getId();

        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Desactiva el usuario
        user.setActive(false);
        userRepository.save(user);
    }
}
