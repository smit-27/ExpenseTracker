package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetReminder;
import com.expensetracker.model.BudgetReminderLog;
import com.expensetracker.model.CategoryType;
import com.expensetracker.repository.BudgetReminderLogRepository;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetReminderService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetReminderLogRepository reminderLogRepository;

    public BudgetReminderService(
            BudgetRepository budgetRepository,
            TransactionRepository transactionRepository,
            BudgetReminderLogRepository reminderLogRepository) {

        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.reminderLogRepository = reminderLogRepository;
    }

    public List<BudgetReminder> getReminders(Integer userId) {

        List<Budget> budgets =
                budgetRepository
                        .findByUserUserIdOrderByBudgetMonthDesc(userId);

        List<BudgetReminder> reminders = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Budget budget : budgets) {

            LocalDate startDate =
                    budget.getBudgetMonth()
                            .withDayOfMonth(1);

            LocalDate endDate =
                    startDate.plusMonths(1);

            // Only check the current month
            if (!startDate.equals(
                    today.withDayOfMonth(1))) {
                continue;
            }

            BigDecimal spent =
                    transactionRepository
                            .getTotalSpentForCategoryAndMonth(
                                    userId,
                                    budget.getCategory()
                                            .getCategoryId(),
                                    CategoryType.EXPENSE,
                                    startDate,
                                    endDate
                            );

            BigDecimal budgetAmount =
                    budget.getBudgetAmount();

            BigDecimal remaining =
                    budgetAmount.subtract(spent);

            double usage =
                    spent
                            .divide(
                                    budgetAmount,
                                    4,
                                    RoundingMode.HALF_UP
                            )
                            .doubleValue()
                            * 100;

            String message;

            if (spent.compareTo(budgetAmount) >= 0) {

                message =
                        "Budget exceeded!";

            } else if (usage >= 80) {

                message =
                        "You have used "
                                + String.format("%.1f", usage)
                                + "% of your budget.";

            } else {

                continue;
            }

            String reminderType;

            if (spent.compareTo(budgetAmount) >= 0) {
                reminderType = "EXCEEDED";
            } else {
                reminderType = "WARNING";
            }

            LocalDateTime cooldownTime =
                    LocalDateTime.now().minusHours(24);

            boolean recentlyReminded =
                    reminderLogRepository
                            .existsByBudgetBudgetIdAndReminderTypeAndRemindedAtAfter(
                                    budget.getBudgetId(),
                                    reminderType,
                                    cooldownTime
                            );

            if (recentlyReminded) {
                continue;
            }

            BudgetReminder reminder =
                    new BudgetReminder(
                            budget.getBudgetId(),
                            budget.getCategory().getName(),
                            budgetAmount,
                            spent,
                            remaining,
                            usage,
                            message
                    );

            reminders.add(reminder);

            BudgetReminderLog log =
                    new BudgetReminderLog();

            log.setBudget(budget);
            log.setReminderType(reminderType);
            log.setRemindedAt(LocalDateTime.now());

            reminderLogRepository.save(log);
        }

        return reminders;
    }
}