package com.expensetracker.model;

import java.math.BigDecimal;

public class MonthlyReport {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private BigDecimal expensePercentage;

    public MonthlyReport(
            BigDecimal totalIncome,
            BigDecimal totalExpense) {

        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = totalIncome.subtract(totalExpense);

        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            this.expensePercentage = BigDecimal.ZERO;
        } else {
            this.expensePercentage =
                    totalExpense
                            .multiply(BigDecimal.valueOf(100))
                            .divide(totalIncome, 2, java.math.RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getExpensePercentage() {
        return expensePercentage;
    }
}