package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = """
        SELECT
            b.budget_id AS budgetId,
            c.name AS categoryName,
            b.budget_amount AS budgetAmount,
            COALESCE(SUM(
                CASE
                    WHEN c.type = 'EXPENSE'
                    THEN t.amount
                    ELSE 0
                END
            ), 0) AS spentAmount
        FROM budgets b
        JOIN categories c
            ON b.category_id = c.category_id
        LEFT JOIN transactions t
            ON t.category_id = c.category_id
            AND t.user_id = b.user_id
            AND YEAR(t.transaction_date) = YEAR(b.budget_month)
            AND MONTH(t.transaction_date) = MONTH(b.budget_month)
        WHERE b.user_id = :userId
        GROUP BY
            b.budget_id,
            c.name,
            b.budget_amount
        ORDER BY b.budget_month DESC
        """, nativeQuery = true)
    List<Object[]> getBudgetReport(
            @Param("userId") Integer userId
    );
}