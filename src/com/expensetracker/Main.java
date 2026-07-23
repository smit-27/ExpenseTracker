package com.expensetracker;

import com.expensetracker.model.Category;
import com.expensetracker.model.Transaction;
import com.expensetracker.model.TransactionView;
import com.expensetracker.service.CategoryService;
import com.expensetracker.service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryService categoryService = new CategoryService();
    private static final TransactionService transactionService =
            new TransactionService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Add Transactions");
            System.out.println("4. View Transactions");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> addCategory();

                case 2 -> viewCategories();

                case 3 -> addTransaction();

                case 4 -> viewTransactions();

                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCategory() {

        System.out.print("Enter category name: ");
        String name = scanner.nextLine();

        System.out.println("1. INCOME");
        System.out.println("2. EXPENSE");
        System.out.print("Choose type: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String type = (choice == 1) ? "INCOME" : "EXPENSE";

        Category category =
                new Category(0, 1, name, type);

        if (categoryService.addCategory(category))
            System.out.println("Category added successfully.");
        else
            System.out.println("Failed to add category.");
    }

    private static void viewCategories() {

        List<Category> categories =
                categoryService.getAllCategories(1);

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        categories.forEach(System.out::println);
    }

    private static void addTransaction() {

        List<Category> categories = categoryService.getAllCategories(1);

        if (categories.isEmpty()) {
            System.out.println("Please create a category first.");
            return;
        }

        System.out.println("\nAvailable Categories");

        for (Category category : categories) {
            System.out.printf("%d. %s (%s)%n",
                    category.getCategoryId(),
                    category.getName(),
                    category.getType());
        }

        System.out.print("\nEnter Category ID: ");
        int categoryId = scanner.nextInt();

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Transaction transaction = new Transaction(
                0,
                1,
                categoryId,
                amount,
                description,
                LocalDate.now()
        );

        if (transactionService.addTransaction(transaction))
            System.out.println("Transaction added.");
        else
            System.out.println("Failed.");
    }

    private static void viewTransactions() {

        List<TransactionView> transactions =
                transactionService.getTransactionViews(1);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        transactions.forEach(System.out::println);
    }
}