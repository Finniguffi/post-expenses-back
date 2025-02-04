package com.expenses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_entity")
public class TransactionEntity extends TransactionBaseEntity {
}