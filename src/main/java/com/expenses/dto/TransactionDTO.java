package com.expenses.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private String userEmail;

    public TransactionDTO(Long id, double amount, String description, LocalDateTime transactionDate, String userEmail) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.userEmail = userEmail;
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
}