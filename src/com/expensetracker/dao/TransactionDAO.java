package com.expensetracker.dao;

import com.expensetracker.db.DBConnection;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.TransactionView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean addTransaction(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (user_id, category_id, amount, description, transaction_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, transaction.getUserId());
            ps.setInt(2, transaction.getCategoryId());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.setDate(5, Date.valueOf(transaction.getTransactionDate()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Transaction transaction) {

        String sql = """
            UPDATE transactions
            SET category_id = ?,
                amount = ?,
                description = ?,
                transaction_date = ?
            WHERE transaction_id = ?
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, transaction.getCategoryId());
            ps.setDouble(2, transaction.getAmount());
            ps.setString(3, transaction.getDescription());
            ps.setDate(4, Date.valueOf(transaction.getTransactionDate()));
            ps.setInt(5, transaction.getTransactionId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getAllTransactions(int userId) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
            SELECT transaction_id,
                   user_id,
                   category_id,
                   amount,
                   description,
                   transaction_date
            FROM transactions
            WHERE user_id = ?
            ORDER BY transaction_date DESC
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("transaction_date").toLocalDate()
                );

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<TransactionView> getTransactionViews(int userId) {

        List<TransactionView> transactions = new ArrayList<>();

        String sql = """
            SELECT
                t.transaction_id,
                c.name,
                c.type,
                t.amount,
                t.description,
                t.transaction_date
            FROM transactions t
            JOIN categories c
                ON t.category_id = c.category_id
            WHERE t.user_id = ?
            ORDER BY t.transaction_date DESC,
                     t.transaction_id DESC
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TransactionView transaction = new TransactionView(
                        rs.getInt("transaction_id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("transaction_date").toLocalDate()
                );

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}