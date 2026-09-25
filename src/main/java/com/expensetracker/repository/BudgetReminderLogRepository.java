package com.expensetracker.repository;

import com.expensetracker.model.BudgetReminderLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BudgetReminderLogRepository
        extends JpaRepository<BudgetReminderLog, Integer> {

    boolean existsByBudgetBudgetIdAndReminderTypeAndRemindedAtAfter(
            Integer budgetId,
            String reminderType,
            LocalDateTime after
    );
}