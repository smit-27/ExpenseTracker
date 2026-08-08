package com.expensetracker.model;

public class Dashboard {

    private double totalIncome;
    private double totalExpense;
    private double balance;

    public Dashboard(double totalIncome, double totalExpense) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = totalIncome - totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public double getSavingsRate() {

        if (Double.compare(totalIncome, 0.0) == 0) {
            return 0;
        }

        return (balance / totalIncome) * 100;
    }

    @Override
    public String toString() {
        return """
            ================================
                    DASHBOARD
            ================================

            Total Income  : %.2f
            Total Expense : %.2f
            Balance       : %.2f
            Savings Rate  : %.2f%%

            ================================
            """.formatted(
                totalIncome,
                totalExpense,
                balance,
                getSavingsRate()
        );
    }
}