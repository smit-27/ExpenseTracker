package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.model.Transaction;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository) {

        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction addTransaction(Transaction transaction) {

        Category category = categoryRepository
                .findById(transaction.getCategory().getCategoryId())
                .filter(c -> c.getUserId().equals(transaction.getUserId()))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(Integer userId) {

        return transactionRepository
                .findByUserIdOrderByTransactionDateDesc(userId);
    }

    public Transaction getTransaction(
            Integer transactionId,
            Integer userId) {

        return transactionRepository
                .findById(transactionId)
                .filter(transaction ->
                        transaction.getUserId().equals(userId))
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));
    }

    public Transaction updateTransaction(
            Integer transactionId,
            Transaction updatedTransaction,
            Integer userId) {

        Transaction existing =
                getTransaction(transactionId, userId);

        Category category = categoryRepository
                .findById(
                        updatedTransaction
                                .getCategory()
                                .getCategoryId()
                )
                .filter(c ->
                        c.getUserId().equals(userId))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        existing.setCategory(category);
        existing.setAmount(updatedTransaction.getAmount());
        existing.setDescription(updatedTransaction.getDescription());
        existing.setTransactionDate(
                updatedTransaction.getTransactionDate()
        );

        return transactionRepository.save(existing);
    }

    public void deleteTransaction(
            Integer transactionId,
            Integer userId) {

        Transaction transaction =
                getTransaction(transactionId, userId);

        transactionRepository.delete(transaction);
    }
}