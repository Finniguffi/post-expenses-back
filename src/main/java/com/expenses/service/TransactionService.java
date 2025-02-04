package com.expenses.service;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.entity.TransactionEntity;
import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.exception.ApplicationException;
import com.expenses.repository.TransactionRepository;
import com.expenses.repository.RecurringExpenseRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    RecurringExpenseRepository recurringExpenseRepository;

    public List<TransactionDTO> getTransactionsByYear(String email, int year) {
        try {
            List<TransactionEntity> transactions = transactionRepository.findByYear(email, year);
            List<RecurringExpenseEntity> recurringExpenses = recurringExpenseRepository.findActiveByUserEmail(email);
            return Stream.concat(
                    transactions.stream().map(this::mapToDTO),
                    recurringExpenses.stream().map(this::mapToDTO)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public List<TransactionDTO> getTransactionsByMonth(String email, int year, int month) {
        try {
            List<TransactionEntity> transactions = transactionRepository.findByMonth(email, year, month);
            List<RecurringExpenseEntity> recurringExpenses = recurringExpenseRepository.findActiveByUserEmail(email);
            return Stream.concat(
                    transactions.stream().map(this::mapToDTO),
                    recurringExpenses.stream().map(this::mapToDTO)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public List<TransactionDTO> getAllTransactions(String email) {
        try {
            List<TransactionEntity> transactions = transactionRepository.findAllTransactions(email);
            List<RecurringExpenseEntity> recurringExpenses = recurringExpenseRepository.findActiveByUserEmail(email);
            return Stream.concat(
                    transactions.stream().map(this::mapToDTO),
                    recurringExpenses.stream().map(this::mapToDTO)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    private TransactionDTO mapToDTO(TransactionEntity transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getUser().getEmail(),
                transaction.isDeposit()
        );
    }

    private TransactionDTO mapToDTO(RecurringExpenseEntity recurringExpense) {
        return new TransactionDTO(
                recurringExpense.getId(),
                recurringExpense.getAmount(),
                recurringExpense.getDescription(),
                null,
                recurringExpense.getUser().getEmail(),
                recurringExpense.isDeposit()
        );
    }
}