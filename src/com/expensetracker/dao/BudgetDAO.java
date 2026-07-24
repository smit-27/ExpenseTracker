package com.expensetracker.dao;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    public boolean addBudget(Budget budget) {

        String sql = """
            INSERT INTO budgets
            (user_id, category_id, budget_amount, budget_month)
            VALUES (?, ?, ?, ?)
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, budget.getUserId());
            ps.setInt(2, budget.getCategoryId());
            ps.setDouble(3, budget.getBudgetAmount());
            ps.setDate(4, Date.valueOf(budget.getBudgetMonth()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Budget> getAllBudgets(int userId) {

        List<Budget> budgets = new ArrayList<>();

        String sql = """
            SELECT *
            FROM budgets
            WHERE user_id = ?
            ORDER BY budget_month DESC
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Budget budget = new Budget(
                        rs.getInt("budget_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("budget_amount"),
                        rs.getDate("budget_month").toLocalDate()
                );

                budgets.add(budget);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budgets;
    }

    public List<BudgetView> getBudgetReport(int userId) {

        List<BudgetView> report = new ArrayList<>();

        String sql = """
        SELECT
            c.name AS category_name,
            b.budget_amount,
            b.budget_month,
            COALESCE(SUM(t.amount), 0) AS spent

        FROM budgets b

        JOIN categories c
            ON b.category_id = c.category_id

        LEFT JOIN transactions t
            ON b.category_id = t.category_id
           AND YEAR(t.transaction_date) = YEAR(b.budget_month)
           AND MONTH(t.transaction_date) = MONTH(b.budget_month)

        WHERE b.user_id = ?

        GROUP BY
            b.budget_id,
            c.name,
            b.budget_amount,
            b.budget_month

        ORDER BY b.budget_month DESC, c.name;
        """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BudgetView view = new BudgetView(
                        rs.getString("category_name"),
                        rs.getDouble("budget_amount"),
                        rs.getDouble("spent"),
                        rs.getDate("budget_month").toLocalDate()
                );

                report.add(view);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }
}
