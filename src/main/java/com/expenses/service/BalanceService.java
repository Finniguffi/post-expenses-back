package com.expenses.service;

import com.expenses.dto.TransactionDTO;
import com.expenses.entity.TransactionEntity;
import com.expenses.entity.UserEntity;
import com.expenses.repository.TransactionRepository;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BalanceService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public void processExpense(String email, double amount, TransactionDTO transactionDTO) {
        this.validateInput(email, amount);

        UserEntity user = userService.getUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        double newBalance = calculateNewBalance(user, amount);
        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(newBalance);
        TransactionEntity transaction = createTransaction(user, amount, transactionDTO);

        this.updateBalanceAndRecordTransaction(user, transaction);
    }

    private TransactionEntity createTransaction(UserEntity user, double amount, TransactionDTO transactionDTO) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount);
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setUser(user);
        return transaction;
    }

    @Transactional
    public Double postBalance(String email, double amount) {
        UserEntity user = getUser(email);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        double newBalance = this.getBalance(email) + amount;
        user.setBalance(newBalance);
        userRepository.persist(user);
        return newBalance;
    }

    @Transactional
    public void recordBalance(UserEntity user) {
        userRepository.persist(user);
    }

    @Transactional
    public void recordTransaction(TransactionEntity transaction) {
        transactionRepository.persist(transaction);
    }

    public Double getBalance(String email) {
        UserEntity user = getUser(email);
        return user.getBalance();
    }

    // Auxiliary Functions

    private UserEntity getUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    private void validateInput(String email, double amount) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    private double calculateNewBalance(UserEntity user, double amount) {
        return user.getBalance() - amount;
    }

    private void updateBalanceAndRecordTransaction(UserEntity user, TransactionEntity transaction) {
        userRepository.persist(user);
        transactionRepository.persist(transaction);
    }
}