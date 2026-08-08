package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetService(
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository) {

        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public Budget addBudget(Budget budget) {

        Integer categoryId =
                budget.getCategory().getCategoryId();

        Category category = categoryRepository
                .findById(categoryId)
                .filter(c ->
                        c.getUserId().equals(budget.getUserId()))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        LocalDate month =
                budget.getBudgetMonth()
                        .withDayOfMonth(1);

        if (budgetRepository
                .existsByUserIdAndCategoryCategoryIdAndBudgetMonth(
                        budget.getUserId(),
                        categoryId,
                        month)) {

            throw new RuntimeException(
                    "Budget already exists for this category and month"
            );
        }

        budget.setCategory(category);
        budget.setBudgetMonth(month);

        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets(Integer userId) {

        return budgetRepository
                .findByUserIdOrderByBudgetMonthDesc(userId);
    }

    public Budget getBudget(
            Integer budgetId,
            Integer userId) {

        return budgetRepository
                .findByBudgetIdAndUserId(
                        budgetId,
                        userId
                )
                .orElseThrow(() ->
                        new RuntimeException("Budget not found"));
    }

    public void deleteBudget(
            Integer budgetId,
            Integer userId) {

        Budget budget = getBudget(budgetId, userId);

        budgetRepository.delete(budget);
    }
}