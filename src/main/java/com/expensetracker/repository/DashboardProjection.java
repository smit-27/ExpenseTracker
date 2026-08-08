package com.expensetracker.repository;

import java.math.BigDecimal;

public interface DashboardProjection {

    BigDecimal getTotalIncome();

    BigDecimal getTotalExpense();
}