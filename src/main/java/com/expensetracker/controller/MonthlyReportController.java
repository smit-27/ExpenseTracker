package com.expensetracker.controller;

import com.expensetracker.model.MonthlyReport;
import com.expensetracker.service.MonthlyReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class MonthlyReportController {

    private final MonthlyReportService monthlyReportService;

    public MonthlyReportController(
            MonthlyReportService monthlyReportService) {

        this.monthlyReportService = monthlyReportService;
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReport> getMonthlyReport(
            @RequestParam Integer userId,
            @RequestParam LocalDate month) {

        return ResponseEntity.ok(
                monthlyReportService.getMonthlyReport(
                        userId,
                        month
                )
        );
    }
}