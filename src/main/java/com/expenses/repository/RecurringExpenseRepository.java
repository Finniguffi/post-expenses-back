package com.expenses.repository;

import com.expenses.entity.RecurringExpenseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RecurringExpenseRepository implements PanacheRepository<RecurringExpenseEntity> {

    public List<RecurringExpenseEntity> findByUserEmail(String email) {
        return find("user.email", email).list();
    }

    public List<RecurringExpenseEntity> findActiveByUserEmail(String email) {
        return find("user.email = ?1 and active = true", email).list();
    }
}