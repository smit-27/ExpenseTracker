package com.expensetracker.model;

import java.time.LocalDate;

public class BudgetView {

    private String categoryName;
    private double budgetAmount;
    private double spentAmount;
    private double remainingAmount;
    private LocalDate budgetMonth;

    public BudgetView(String categoryName,
                      double budgetAmount,
                      double spentAmount,
                      LocalDate budgetMonth) {

        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.remainingAmount = budgetAmount - spentAmount;
        this.budgetMonth = budgetMonth;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    public double getUsedPercentage() {

        if (budgetAmount == 0) {
            return 0;
        }

        return (spentAmount / budgetAmount) * 100;
    }

    public String getStatus() {
        return remainingAmount >= 0
                ? "Within Budget"
                : "Budget Exceeded";
    }

    @Override
    public String toString() {
        return """
            ==================================
            Category    : %s
            Month       : %s

            Budget      : £%.2f
            Spent       : £%.2f
            Remaining   : £%.2f
            Used        : %.2f%%
            Status      : %s
            ==================================
            """.formatted(
                categoryName,
                budgetMonth,
                budgetAmount,
                spentAmount,
                remainingAmount,
                getUsedPercentage(),
                getStatus()
        );
    }
}