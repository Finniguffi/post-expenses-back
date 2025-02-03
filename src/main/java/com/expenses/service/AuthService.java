package com.expenses.service;

import com.expenses.dto.LoginDTO;
import com.expenses.dto.RegisterDTO;
import com.expenses.entity.UserEntity;
import com.expenses.repository.UserRepository;
import com.expenses.util.Jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {
    @Inject
    UserRepository userRepository;

    @Transactional
    public UserEntity register(RegisterDTO registerDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        userRepository.persist(user);
        return user;
    }

    public String authenticate(LoginDTO loginDTO) {
        UserEntity user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            return Jwt.generateToken(user.getEmail());
        }
        return null;
    }
}