package com.expenses.service;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.LoginDTO;
import com.expenses.dto.RegisterDTO;
import com.expenses.entity.UserEntity;
import com.expenses.exception.ApplicationException;
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
        try {
            UserEntity user = new UserEntity();
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            userRepository.persist(user);
            return user;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public String authenticate(LoginDTO loginDTO) {
        try {
            UserEntity user = userRepository.findByEmail(loginDTO.getEmail());
            if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
                throw new ApplicationException(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE);
            }
            return Jwt.generateToken(user.getEmail());
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}