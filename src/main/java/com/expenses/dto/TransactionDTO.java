package com.expenses.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private String userEmail;
    private boolean isDeposit;
    private String category;
    private String subCategory;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, double amount, String description, LocalDateTime transactionDate, String userEmail, boolean isDeposit, String category, String subCategory) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.userEmail = userEmail;
        this.isDeposit = isDeposit;
        this.category = category;
        this.subCategory = subCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    public void setDeposit(boolean isDeposit) {
        this.isDeposit = isDeposit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public static class Builder {
        private Long id;
        private double amount;
        private String description;
        private LocalDateTime transactionDate;
        private String userEmail;
        private boolean isDeposit;
        private String category;
        private String subCategory;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setTransactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public Builder setUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder setDeposit(boolean isDeposit) {
            this.isDeposit = isDeposit;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setSubCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public TransactionDTO build() {
            return new TransactionDTO(this);
        }
    }

    private TransactionDTO(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.description = builder.description;
        this.transactionDate = builder.transactionDate;
        this.userEmail = builder.userEmail;
        this.isDeposit = builder.isDeposit;
        this.category = builder.category;
        this.subCategory = builder.subCategory;
    }
}