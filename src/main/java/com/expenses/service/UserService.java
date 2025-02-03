package com.expenses.service;

import com.expenses.entity.UserEntity;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}