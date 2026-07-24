package com.expensetracker.dao;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {
    public Dashboard getDashboard(int userId) {

        String sql = """
            SELECT
                SUM(CASE
                        WHEN c.type='INCOME'
                        THEN t.amount
                        ELSE 0
                    END) AS income,

                SUM(CASE
                        WHEN c.type='EXPENSE'
                        THEN t.amount
                        ELSE 0
                    END) AS expense

            FROM transactions t
            JOIN categories c
                ON t.category_id = c.category_id

            WHERE t.user_id = ?
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                double income = rs.getDouble("income");
                if (rs.wasNull()) income = 0;

                double expense = rs.getDouble("expense");
                if (rs.wasNull()) expense = 0;

                return new Dashboard(income, expense);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Dashboard(0, 0);
    }
}
