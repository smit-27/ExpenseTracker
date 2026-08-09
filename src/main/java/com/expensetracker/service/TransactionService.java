package com.expensetracker.service;

import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.model.Category;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.User;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.TransactionRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Transaction addTransaction(Transaction transaction) {

        Integer userId = transaction.getUser().getUserId();

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Category category = categoryRepository
                .findById(transaction.getCategory().getCategoryId())
                .filter(c ->
                        c.getUser().getUserId().equals(userId))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        transaction.setUser(user);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(Integer userId) {

        return transactionRepository
                .findByUserUserIdOrderByTransactionDateDesc(userId);
    }

    public Transaction getTransaction(
            Integer transactionId,
            Integer userId) {

        return transactionRepository
                .findById(transactionId)
                .filter(transaction ->
                        transaction.getUser().getUserId().equals(userId))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"
                        ));
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
                        c.getUser().getUserId().equals(userId))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));

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