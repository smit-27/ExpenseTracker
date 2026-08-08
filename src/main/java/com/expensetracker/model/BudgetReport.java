package com.expensetracker.model;

import java.math.BigDecimal;

public class BudgetReport {

    private Integer budgetId;
    private String categoryName;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;

    public BudgetReport(
            Integer budgetId,
            String categoryName,
            BigDecimal budgetAmount,
            BigDecimal spentAmount) {

        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.remainingAmount =
                budgetAmount.subtract(spentAmount);
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
}