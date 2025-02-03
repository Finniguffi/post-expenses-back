package com.expenses.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private String userEmail;
    private boolean isRecurring;

    public TransactionDTO(Long id, double amount, String description, LocalDateTime transactionDate, String userEmail, boolean isRecurring) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.userEmail = userEmail;
        this.isRecurring = isRecurring;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
}