package com.nocountry.quo.service;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto registerNewUser(String username, String password, String mail, String phone, String avatar) {
        // Encriptar la contraseña
        String encryptedPassword = passwordEncoder.encode(password);

        // Crear el nuevo usuario
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encryptedPassword);
        newUser.setMail(mail);
        newUser.setPhone(phone);
        newUser.setAvatar(avatar);

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(newUser);

        // Usar el constructor del DTO que acepta un objeto User
        return new UserResponseDto(savedUser);
    }
}
