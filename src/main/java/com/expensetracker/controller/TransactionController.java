package com.expensetracker.controller;

import com.expensetracker.model.Transaction;
import com.expensetracker.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(
            TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody Transaction transaction) {

        Transaction saved =
                transactionService.addTransaction(transaction);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                transactionService.getAllTransactions(userId)
        );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(
            @PathVariable Integer transactionId,
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                transactionService.getTransaction(
                        transactionId,
                        userId
                )
        );
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Integer transactionId,
            @RequestParam Integer userId,
            @RequestBody Transaction transaction) {

        return ResponseEntity.ok(
                transactionService.updateTransaction(
                        transactionId,
                        transaction,
                        userId
                )
        );
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Integer transactionId,
            @RequestParam Integer userId) {

        transactionService.deleteTransaction(
                transactionId,
                userId
        );

        return ResponseEntity.noContent().build();
    }
}