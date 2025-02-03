package com.expenses.service;

import com.expenses.dto.TransactionDTO;
import com.expenses.entity.TransactionEntity;
import com.expenses.repository.TransactionRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    public List<TransactionDTO> getTransactionsByYear(String email, int year) {
        List<TransactionEntity> transactions = transactionRepository.findByYear(email, year);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByMonth(String email, int year, int month) {
        List<TransactionEntity> transactions = transactionRepository.findByMonth(email, year, month);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getAllTransactions(String email) {
        List<TransactionEntity> transactions = transactionRepository.findAllTransactions(email);
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO mapToDTO(TransactionEntity transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getUser().getEmail()
        );
    }
}