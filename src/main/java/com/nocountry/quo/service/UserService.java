package com.nocountry.quo.service;

import com.nocountry.quo.model.User.User;
import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.model.User.UserUpdateDto;
import com.nocountry.quo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private User getAuthenticatedUser(UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }
    
    public UserResponseDto read(UserDetails userDetails) {
        User user = getAuthenticatedUser(userDetails);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateInfo(UserDetails userDetails, UserUpdateDto userDto) {
        User user = getAuthenticatedUser(userDetails);
        user.setUsername(userDto.username());
        user.setPhone(userDto.phone());
        user.setMail(userDto.mail());
        return new UserResponseDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto updateAvatar(UserDetails userDetails, String avatar) {
        User user = getAuthenticatedUser(userDetails);
        user.setAvatar(avatar);
        return new UserResponseDto(userRepository.save(user));
    }

    @Transactional
    public void deactivateAccount(UserDetails userDetails) {
        User user = getAuthenticatedUser(userDetails);
        user.setActive(false);
        userRepository.save(user);
    }
}
