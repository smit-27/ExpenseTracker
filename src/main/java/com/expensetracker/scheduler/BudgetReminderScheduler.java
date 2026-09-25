package com.expensetracker.scheduler;

import com.expensetracker.model.BudgetReminder;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetReminderScheduler {

    private final BudgetRepository budgetRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(BudgetReminderScheduler.class);

    private final BudgetReminderService budgetReminderService;

    public BudgetReminderScheduler(
            BudgetReminderService budgetReminderService,
            BudgetRepository budgetRepository) {

        this.budgetReminderService = budgetReminderService;
        this.budgetRepository = budgetRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkBudgets() {

        logger.info("Running monthly budget reminder check...");

        List<Integer> userIds =
                budgetRepository.findAllUserIds();

        for (Integer userId : userIds) {

            List<BudgetReminder> reminders =
                    budgetReminderService.getReminders(userId);

            for (BudgetReminder reminder : reminders) {

                logger.warn(
                        "BUDGET ALERT | User: {} | Category: {} | Spent: {} | Budget: {} | Usage: {}% | {}",
                        userId,
                        reminder.getCategoryName(),
                        reminder.getSpentAmount(),
                        reminder.getBudgetAmount(),
                        String.format(
                                "%.1f",
                                reminder.getUsagePercentage()
                        ),
                        reminder.getMessage()
                );
            }
        }

        logger.info("Budget reminder check completed.");
    }
}