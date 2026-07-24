package com.expensetracker.model;

import java.time.LocalDate;

public class Budget {

    private int budgetId;
    private int userId;
    private int categoryId;
    private double budgetAmount;
    private LocalDate budgetMonth;

    public Budget(int budgetId,
                  int userId,
                  int categoryId,
                  double budgetAmount,
                  LocalDate budgetMonth) {

        this.budgetId = budgetId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetAmount = budgetAmount;
        this.budgetMonth = budgetMonth;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    @Override
    public String toString() {
        return """
                Budget[
                    budgetId=%d,
                    categoryId=%d,
                    amount=%.2f,
                    month=%s
                ]
                """.formatted(
                budgetId,
                categoryId,
                budgetAmount,
                budgetMonth
        );
    }
}