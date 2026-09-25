package com.expensetracker.service;

import com.expensetracker.model.Dashboard;
import com.expensetracker.repository.DashboardProjection;
import com.expensetracker.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public Dashboard getDashboard(Integer userId) {

        DashboardProjection result =
                dashboardRepository.getDashboardData(userId);

        BigDecimal totalIncome =
                result.getTotalIncome() != null
                        ? result.getTotalIncome()
                        : BigDecimal.ZERO;

        BigDecimal totalExpense =
                result.getTotalExpense() != null
                        ? result.getTotalExpense()
                        : BigDecimal.ZERO;

        return new Dashboard(
                totalIncome,
                totalExpense
        );
    }
}