package com.expenses.service;

import com.expenses.constants.ErrorConstants;
import com.expenses.dto.TransactionDTO;
import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.entity.UserEntity;
import com.expenses.exception.ApplicationException;
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
        try {
            UserEntity user = userRepository.find("email", transactionDTO.getUserEmail()).firstResult();
            if (user == null) {
                throw new ApplicationException(ErrorConstants.USER_NOT_FOUND_CODE, ErrorConstants.USER_NOT_FOUND_MESSAGE);
            }

            RecurringExpenseEntity recurringExpense = new RecurringExpenseEntity();
            recurringExpense.setAmount(transactionDTO.getAmount());
            recurringExpense.setDescription(transactionDTO.getDescription());
            recurringExpense.setDayOfMonth(dayOfMonth);
            recurringExpense.setActive(true);
            recurringExpense.setUser(user);

            recurringExpenseRepository.persist(recurringExpense);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public List<RecurringExpenseEntity> getRecurringExpenses(String email) {
        try {
            return recurringExpenseRepository.findByUserEmail(email);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    public List<RecurringExpenseEntity> getActiveRecurringExpenses(String email) {
        try {
            return recurringExpenseRepository.findActiveByUserEmail(email);
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }

    @Transactional
    public void disableRecurringExpense(Long id) {
        try {
            RecurringExpenseEntity recurringExpense = recurringExpenseRepository.findById(id);
            if (recurringExpense == null) {
                throw new ApplicationException(ErrorConstants.RECURRING_EXPENSE_NOT_FOUND_CODE, ErrorConstants.RECURRING_EXPENSE_NOT_FOUND_MESSAGE);
            }
            recurringExpense.setActive(false);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, e);
        }
    }
}