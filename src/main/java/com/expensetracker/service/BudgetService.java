package com.expensetracker.service;

import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetReport;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BudgetService(
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Budget addBudget(
            Budget budget,
            Integer userId) {

        Integer categoryId =
                budget.getCategory().getCategoryId();

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Category category = categoryRepository
                .findById(categoryId)
                .filter(c ->
                        c.getUser()
                                .getUserId()
                                .equals(userId))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));

        LocalDate month =
                budget.getBudgetMonth()
                        .withDayOfMonth(1);

        if (budgetRepository
                .existsByUserUserIdAndCategoryCategoryIdAndBudgetMonth(
                        userId,
                        categoryId,
                        month)) {

            throw new DuplicateResourceException(
                    "Budget already exists for this category and month"
            );
        }

        budget.setUser(user);
        budget.setCategory(category);
        budget.setBudgetMonth(month);

        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets(Integer userId) {

        return budgetRepository
                .findByUserUserIdOrderByBudgetMonthDesc(userId);
    }

    public Budget getBudget(
            Integer budgetId,
            Integer userId) {

        return budgetRepository
                .findByBudgetIdAndUserUserId(
                        budgetId,
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Budget not found"
                        ));
    }

    public void deleteBudget(
            Integer budgetId,
            Integer userId) {

        Budget budget = getBudget(budgetId, userId);

        budgetRepository.delete(budget);
    }

    public List<BudgetReport> getBudgetReport(Integer userId) {

        List<Object[]> rows =
                budgetRepository.getBudgetReport(userId);

        return rows.stream()
                .map(row -> new BudgetReport(
                        ((Number) row[0]).intValue(),
                        (String) row[1],
                        (BigDecimal) row[2],
                        (BigDecimal) row[3]
                ))
                .toList();
    }
}