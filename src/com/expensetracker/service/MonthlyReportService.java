package com.expensetracker.service;

import com.expensetracker.dao.MonthlyReportDAO;
import com.expensetracker.model.MonthlyReport;

import java.time.LocalDate;

public class MonthlyReportService {

    private MonthlyReportDAO monthlyReportDAO;

    public MonthlyReportService() {
        monthlyReportDAO = new MonthlyReportDAO();
    }

    public MonthlyReport getMonthlyReport(int userId, LocalDate month) {
        return monthlyReportDAO.getMonthlyReport(userId, month);
    }
}