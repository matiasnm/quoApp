package com.nocountry.quo.service;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto read(Long id, UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();

        if (id != userId) {
            throw new AccessDeniedException("Unauthorized.");
        }

        Optional<User> userOptional = userRepository.findById(id);
    
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with ID=" + id + " not found.");
        }
    
        User user = userOptional.get();
        return new UserResponseDto(user);
    }

}