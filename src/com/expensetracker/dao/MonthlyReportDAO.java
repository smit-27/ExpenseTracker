package com.expensetracker.dao;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.MonthlyReport;

import java.sql.*;
import java.time.LocalDate;

public class MonthlyReportDAO {
    public MonthlyReport getMonthlyReport(int userId, LocalDate month) {

        String sql = """
        SELECT
            COALESCE(SUM(
                CASE
                    WHEN c.type = 'INCOME' THEN t.amount
                    ELSE 0
                END
            ), 0) AS income,

            COALESCE(SUM(
                CASE
                    WHEN c.type = 'EXPENSE' THEN t.amount
                    ELSE 0
                END
            ), 0) AS expense

        FROM transactions t

        JOIN categories c
            ON t.category_id = c.category_id

        WHERE t.user_id = ?
          AND YEAR(t.transaction_date) = YEAR(?)
          AND MONTH(t.transaction_date) = MONTH(?);
        """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(month));
            ps.setDate(3, Date.valueOf(month));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new MonthlyReport(
                        month,
                        rs.getDouble("income"),
                        rs.getDouble("expense")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new MonthlyReport(month, 0, 0);
    }
}
