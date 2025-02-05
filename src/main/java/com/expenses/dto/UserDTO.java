package com.expenses.dto;

public class UserDTO {
    private Long id;
    private String email;
    private Double balance;

    private UserDTO(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.balance = builder.balance;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Double getBalance() {
        return balance;
    }

    public static class Builder {
        private Long id;
        private String email;
        private Double balance;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}