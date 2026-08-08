package com.expensetracker.repository;

import java.math.BigDecimal;

public interface MonthlyReportProjection {

    BigDecimal getTotalIncome();

    BigDecimal getTotalExpense();
}