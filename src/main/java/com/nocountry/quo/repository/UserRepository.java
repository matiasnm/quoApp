package com.nocountry.quo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.nocountry.quo.model.User.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByUsername(String username);
    
}