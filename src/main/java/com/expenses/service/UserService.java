package com.expenses.service;

import com.expenses.dto.UserDTO;
import com.expenses.entity.UserEntity;
import com.expenses.repository.UserRepository;
import com.expenses.exception.ApplicationException;
import com.expenses.constants.ErrorConstants;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public UserDTO getUserByEmail(String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) {
                throw new ApplicationException(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE);
            }

            UserDTO userDTO = new UserDTO.Builder()
                    .setId(userEntity.getId())
                    .setEmail(userEntity.getEmail())
                    .setBalance(userEntity.getBalance())
                    .build();

            return userDTO;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}