package com.expensetracker.model;

import java.math.BigDecimal;

public class BudgetReminder {

    private Integer budgetId;
    private String categoryName;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private double usagePercentage;
    private String message;

    public BudgetReminder() {
    }

    public BudgetReminder(
            Integer budgetId,
            String categoryName,
            BigDecimal budgetAmount,
            BigDecimal spentAmount,
            BigDecimal remainingAmount,
            double usagePercentage,
            String message) {

        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.remainingAmount = remainingAmount;
        this.usagePercentage = usagePercentage;
        this.message = message;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public double getUsagePercentage() {
        return usagePercentage;
    }

    public String getMessage() {
        return message;
    }
}