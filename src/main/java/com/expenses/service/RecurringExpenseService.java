package com.expenses.service;

import com.expenses.dto.TransactionDTO;
import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.entity.UserEntity;
import com.expenses.repository.RecurringExpenseRepository;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RecurringExpenseService {

    @Inject
    RecurringExpenseRepository recurringExpenseRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public void createRecurringExpense(TransactionDTO transactionDTO, int dayOfMonth) {
        UserEntity user = userRepository.find("email", transactionDTO.getUserEmail()).firstResult();
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        RecurringExpenseEntity recurringExpense = new RecurringExpenseEntity();
        recurringExpense.setAmount(transactionDTO.getAmount());
        recurringExpense.setDescription(transactionDTO.getDescription());
        recurringExpense.setDayOfMonth(dayOfMonth);
        recurringExpense.setActive(true);
        recurringExpense.setUser(user);

        recurringExpenseRepository.persist(recurringExpense);
    }

    public List<RecurringExpenseEntity> getRecurringExpenses(String email) {
        return recurringExpenseRepository.findByUserEmail(email);
    }

    public List<RecurringExpenseEntity> getActiveRecurringExpenses(String email) {
        return recurringExpenseRepository.findActiveByUserEmail(email);
    }

    @Transactional
    public void disableRecurringExpense(Long id) {
        RecurringExpenseEntity recurringExpense = recurringExpenseRepository.findById(id);
        if (recurringExpense != null) {
            recurringExpense.setActive(false);
        }
    }
}