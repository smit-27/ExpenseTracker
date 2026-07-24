package com.expensetracker.model;

import java.time.LocalDate;

public class MonthlyReport {

    private LocalDate month;
    private double totalIncome;
    private double totalExpense;
    private double savings;

    public MonthlyReport(LocalDate month,
                         double totalIncome,
                         double totalExpense) {

        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.savings = totalIncome - totalExpense;
    }

    public LocalDate getMonth() {
        return month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getSavings() {
        return savings;
    }

    @Override
    public String toString() {
        return """
                =============================
                Monthly Report
                =============================
                Month         : %s
                Total Income  : £%.2f
                Total Expense : £%.2f
                Savings       : £%.2f
                """.formatted(
                month,
                totalIncome,
                totalExpense,
                savings
        );
    }
}