package com.expenses.service;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.entity.TransactionEntity;
import com.expenses.entity.UserEntity;
import com.expenses.exception.ApplicationException;
import com.expenses.repository.TransactionRepository;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class BalanceService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public double processExpense(String email, TransactionDTO transactionDTO) {
        try {
            this.validateInput(email, transactionDTO.getAmount());

            UserEntity user = userService.getUserByEmail(email);
            if (user == null) {
                throw new ApplicationException(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE);
            }

            double newBalance = calculateNewBalance(user, transactionDTO.getAmount());
            if (newBalance < 0) {
                throw new ApplicationException(ErrorConstants.INSUFFICIENT_BALANCE_CODE, ErrorConstants.INSUFFICIENT_BALANCE_MESSAGE);
            }

            user.setBalance(newBalance);
            TransactionEntity transaction = createTransaction(user, transactionDTO.getAmount(), transactionDTO, false);

            this.updateBalanceAndRecordTransaction(user, transaction);

            return newBalance;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    private TransactionEntity createTransaction(UserEntity user, double amount, TransactionDTO transactionDTO, boolean isDeposit) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount);
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setDeposit(isDeposit);
        return transaction;
    }

    @Transactional
    public Double postBalance(String email, double amount) {
        try {
            UserEntity user = getUser(email);
            if (amount <= 0) {
                throw new ApplicationException(ErrorConstants.INVALID_AMOUNT_CODE, ErrorConstants.INVALID_AMOUNT_MESSAGE);
            }
            double newBalance = this.getBalance(email) + amount;
            user.setBalance(newBalance);

            TransactionEntity transaction = createTransaction(user, amount, new TransactionDTO(null, amount, "Deposit", null, email, false), true);
            transactionRepository.persist(transaction);

            userRepository.persist(user);
            return newBalance;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void recordBalance(UserEntity user) {
        try {
            userRepository.persist(user);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void recordTransaction(TransactionEntity transaction) {
        try {
            transactionRepository.persist(transaction);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public Double getBalance(String email) {
        try {
            UserEntity user = getUser(email);
            return user.getBalance();
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    private UserEntity getUser(String email) {
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

    private void validateInput(String email, double amount) {
        if (email == null || email.isEmpty()) {
            throw new ApplicationException(ErrorConstants.INVALID_AMOUNT_CODE, "Email is required");
        }
        if (amount <= 0) {
            throw new ApplicationException(ErrorConstants.INVALID_AMOUNT_CODE, ErrorConstants.INVALID_AMOUNT_MESSAGE);
        }
    }

    private double calculateNewBalance(UserEntity user, double amount) {
        return user.getBalance() - amount;
    }

    private void updateBalanceAndRecordTransaction(UserEntity user, TransactionEntity transaction) {
        try {
            userRepository.persist(user);
            transactionRepository.persist(transaction);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}