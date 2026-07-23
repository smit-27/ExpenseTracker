package com.expensetracker.model;

import java.time.LocalDate;

public class TransactionView {

    private int transactionId;
    private String categoryName;
    private String categoryType;
    private double amount;
    private String description;
    private LocalDate transactionDate;

    public TransactionView(int transactionId,
                           String categoryName,
                           String categoryType,
                           double amount,
                           String description,
                           LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return """
                ----------------------------------------
                Date        : %s
                Category    : %s
                Type        : %s
                Amount      : ₹%.2f
                Description : %s
                ----------------------------------------
                """.formatted(
                transactionDate,
                categoryName,
                categoryType,
                amount,
                description
        );
    }
}