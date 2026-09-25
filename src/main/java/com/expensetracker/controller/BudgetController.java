package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetReport;
import com.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
            @Valid @RequestBody Budget budget,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        budget.setUser(
                budgetService.getUser(
                        userId.intValue()
                )
        );

        Budget saved =
                budgetService.addBudget(budget);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        return ResponseEntity.ok(
                budgetService.getAllBudgets(
                        userId.intValue()
                )
        );
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudget(
            @PathVariable Integer budgetId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        return ResponseEntity.ok(
                budgetService.getBudget(
                        budgetId,
                        userId.intValue()
                )
        );
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Integer budgetId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        budgetService.deleteBudget(
                budgetId,
                userId.intValue()
        );

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/report")
    public ResponseEntity<List<BudgetReport>> getBudgetReport(
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        return ResponseEntity.ok(
                budgetService.getBudgetReport(
                        userId.intValue()
                )
        );
    }
}