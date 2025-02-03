package com.expenses.job;

import com.expenses.entity.RecurringExpenseEntity;
import com.expenses.entity.TransactionEntity;
import com.expenses.repository.RecurringExpenseRepository;
import com.expenses.repository.TransactionRepository;
import com.expenses.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@ApplicationScoped
public class RecurringExpenseJob implements Job {

    @Inject
    RecurringExpenseRepository recurringExpenseRepository;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<RecurringExpenseEntity> recurringExpenses = recurringExpenseRepository.find("active", true).list();
        LocalDateTime now = LocalDateTime.now();
        for (RecurringExpenseEntity recurringExpense : recurringExpenses) {
            if (recurringExpense.getDayOfMonth() == now.getDayOfMonth()) {
                TransactionEntity transaction = new TransactionEntity();
                transaction.setAmount(recurringExpense.getAmount());
                transaction.setDescription(recurringExpense.getDescription());
                transaction.setTransactionDate(now);
                transaction.setUser(recurringExpense.getUser());
                transactionRepository.persist(transaction);
            }
        }
    }
}