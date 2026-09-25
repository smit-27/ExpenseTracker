package com.expensetracker.controller;

import com.expensetracker.model.BudgetReminder;
import com.expensetracker.service.BudgetReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-reminders")
public class BudgetReminderController {

    private final BudgetReminderService budgetReminderService;

    public BudgetReminderController(
            BudgetReminderService budgetReminderService) {

        this.budgetReminderService =
                budgetReminderService;
    }

    @GetMapping
    public ResponseEntity<List<BudgetReminder>> getReminders(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                budgetReminderService.getReminders(userId)
        );
    }
}