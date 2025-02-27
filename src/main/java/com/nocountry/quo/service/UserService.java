package com.nocountry.quo.service;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // Método para obtener el usuario por ID
    public UserResponseDto read(Long id) {
        return new UserResponseDto(userRepository.getReferenceById(id));
    }

    // Método para actualizar el nombre y teléfono del usuario
    @Transactional
    public User updateUserInfo(Long userId, String username, String phone) {
        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar los campos de nombre y teléfono
        user.setUsername(username);
        user.setPhone(phone);

        // Guardar los cambios en la base de datos
        return userRepository.save(user);
    }

    // Método para actualizar el avatar del usuario
    @Transactional
    public User updateAvatar(Long userId, String avatar) {
        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el campo del avatar
        user.setAvatar(avatar);

        // Guardar los cambios en la base de datos
        return userRepository.save(user);
    }

    // Método para eliminar la cuenta del usuario
    @Transactional
    public void deleteAccount(Long userId) {
        // Buscar al usuario en la base de datos
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminar el usuario de la base de datos
        userRepository.delete(user);
    }
}
