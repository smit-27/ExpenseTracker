package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    List<Budget> findByUserIdOrderByBudgetMonthDesc(Integer userId);

    Optional<Budget> findByBudgetIdAndUserId(
            Integer budgetId,
            Integer userId
    );

    boolean existsByUserIdAndCategoryCategoryIdAndBudgetMonth(
            Integer userId,
            Integer categoryId,
            LocalDate budgetMonth
    );
}