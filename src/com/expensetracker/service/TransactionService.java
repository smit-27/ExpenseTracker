package com.expensetracker.service;

import com.expensetracker.dao.TransactionDAO;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.TransactionView;

import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    public boolean addTransaction(Transaction transaction) {
        return transactionDAO.addTransaction(transaction);
    }

    public boolean updateTransaction(Transaction transaction) {
        return transactionDAO.updateTransaction(transaction);
    }

    public List<Transaction> getAllTransactions(int userId) {
        return transactionDAO.getAllTransactions(userId);
    }

    public List<TransactionView> getTransactionViews(int userId) {
        return transactionDAO.getTransactionViews(userId);
    }
}