package com.expenses.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.expenses.entity.TransactionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<TransactionEntity> {

    public List<TransactionEntity> findByYear(String email, int year) {
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year + 1, 1, 1, 0, 0);
        return find("user.email = ?1 and transactionDate >= ?2 and transactionDate < ?3", email, start, end).list();
    }

    public List<TransactionEntity> findByMonth(String email, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);
        return find("user.email = ?1 and transactionDate >= ?2 and transactionDate < ?3", email, start, end).list();
    }

    public List<TransactionEntity> findAllTransactions(String email) {
        return find("user.email", email).list();
    }
}