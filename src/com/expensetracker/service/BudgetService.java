package com.expensetracker.service;

import com.expensetracker.dao.BudgetDAO;
import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetView;

import java.util.List;

public class BudgetService {

    private BudgetDAO budgetDAO;

    public BudgetService() {
        budgetDAO = new BudgetDAO();
    }

    public boolean addBudget(Budget budget) {
        return budgetDAO.addBudget(budget);
    }

    public List<Budget> getAllBudgets(int userId) {
        return budgetDAO.getAllBudgets(userId);
    }

    public List<BudgetView> getBudgetReport(int userId) {
        return budgetDAO.getBudgetReport(userId);
    }
}