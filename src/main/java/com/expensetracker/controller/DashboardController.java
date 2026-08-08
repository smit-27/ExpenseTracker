package com.expensetracker.controller;

import com.expensetracker.model.Dashboard;
import com.expensetracker.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<Dashboard> getDashboard(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(userId)
        );
    }
}