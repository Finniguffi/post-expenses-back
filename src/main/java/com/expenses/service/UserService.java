package com.expenses.service;

import com.expenses.constants.ErrorConstants;
import com.expenses.entity.UserEntity;
import com.expenses.exception.ApplicationException;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public UserEntity getUserByEmail(String email) {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new ApplicationException(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE);
            }
            return user;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}