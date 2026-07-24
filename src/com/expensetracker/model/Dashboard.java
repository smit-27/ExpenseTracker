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

    @Override
    public String toString() {
        return """
                ============================
                Dashboard
                ============================
                Total Income : ₹%.2f
                Total Expense: ₹%.2f
                Balance      : ₹%.2f
                """.formatted(totalIncome, totalExpense, balance);
    }
}