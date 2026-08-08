package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetReport;
import com.expensetracker.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> addBudget(
            @RequestBody Budget budget) {

        Budget saved = budgetService.addBudget(budget);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                budgetService.getAllBudgets(userId)
        );
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudget(
            @PathVariable Integer budgetId,
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                budgetService.getBudget(budgetId, userId)
        );
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Integer budgetId,
            @RequestParam Integer userId) {

        budgetService.deleteBudget(budgetId, userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<List<BudgetReport>> getBudgetReport(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                budgetService.getBudgetReport(userId)
        );
    }
}