package com.expensetracker.service;

import com.expensetracker.dao.DashboardDAO;
import com.expensetracker.model.Dashboard;

public class DashboardService {

    private DashboardDAO dashboardDAO;

    public DashboardService() {
        dashboardDAO = new DashboardDAO();
    }

    public Dashboard getDashboard(int userId) {
        return dashboardDAO.getDashboard(userId);
    }
}