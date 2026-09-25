package com.expensetracker.repository;

import com.expensetracker.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MonthlyReportRepository
        extends Repository<Transaction, Integer> {

    @Query(value = """
            SELECT
                COALESCE(
                    SUM(
                        CASE
                            WHEN c.type = 'INCOME'
                            THEN t.amount
                            ELSE 0
                        END
                    ), 0
                ) AS totalIncome,

                COALESCE(
                    SUM(
                        CASE
                            WHEN c.type = 'EXPENSE'
                            THEN t.amount
                            ELSE 0
                        END
                    ), 0
                ) AS totalExpense

            FROM transactions t
            JOIN categories c
                ON t.category_id = c.category_id
            WHERE t.user_id = :userId
              AND t.transaction_date >= :startDate
              AND t.transaction_date < :endDate
            """, nativeQuery = true)
    MonthlyReportProjection getMonthlyReport(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}