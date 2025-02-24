package com.nocountry.quo.service;

import com.nocountry.quo.model.User.UserResponseDto;
import com.nocountry.quo.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto read(Long id) {
        return new UserResponseDto(userRepository.getReferenceById(id));
    }

}