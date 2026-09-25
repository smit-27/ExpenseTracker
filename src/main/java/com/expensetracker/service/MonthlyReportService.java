package com.expensetracker.service;

import com.expensetracker.model.MonthlyReport;
import com.expensetracker.repository.MonthlyReportProjection;
import com.expensetracker.repository.MonthlyReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MonthlyReportService {

    private final MonthlyReportRepository monthlyReportRepository;

    public MonthlyReportService(
            MonthlyReportRepository monthlyReportRepository) {

        this.monthlyReportRepository = monthlyReportRepository;
    }

    public MonthlyReport getMonthlyReport(
            Integer userId,
            LocalDate month) {

        LocalDate startDate =
                month.withDayOfMonth(1);

        LocalDate endDate =
                startDate.plusMonths(1);

        MonthlyReportProjection result =
                monthlyReportRepository.getMonthlyReport(
                        userId,
                        startDate,
                        endDate
                );

        return new MonthlyReport(
                result.getTotalIncome(),
                result.getTotalExpense()
        );
    }
}