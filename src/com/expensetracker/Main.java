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
            System.out.println("3. Delete Category");
            System.out.println("4. Add Transactions");
            System.out.println("5. View Transactions");
            System.out.println("6. Update Transaction");
            System.out.println("7. Delete Transaction");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> addCategory();

                case 2 -> viewCategories();

                case 3 -> deleteCategory();

                case 4 -> addTransaction();

                case 5 -> viewTransactions();

                case 6 -> updateTransaction();

                case 7 -> deleteTransaction();

                case 8 -> {
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

    private static void deleteCategory() {

        viewCategories();

        System.out.print("\nEnter Category ID to delete: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        if (categoryService.deleteCategory(categoryId))
            System.out.println("Category deleted successfully!");
        else
            System.out.println("Category not found.");
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

    private static void updateTransaction() {

        viewTransactions();

        System.out.print("\nEnter Transaction ID: ");
        int transactionId = scanner.nextInt();

        List<Category> categories = categoryService.getAllCategories(1);

        System.out.println("\nAvailable Categories");

        for (Category category : categories) {
            System.out.printf("%d. %s (%s)%n",
                    category.getCategoryId(),
                    category.getName(),
                    category.getType());
        }

        System.out.print("\nEnter New Category ID: ");
        int categoryId = scanner.nextInt();

        System.out.print("Enter New Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter New Description: ");
        String description = scanner.nextLine();

        Transaction transaction = new Transaction(
                transactionId,
                1,
                categoryId,
                amount,
                description,
                LocalDate.now()
        );

        if(transactionService.updateTransaction(transaction))
            System.out.println("Transaction Updated Successfully!");
        else
            System.out.println("Transaction Not Found.");
    }

    private static void deleteTransaction() {

        viewTransactions();

        System.out.print("\nEnter Transaction ID to delete: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();

        if (transactionService.deleteTransaction(transactionId))
            System.out.println("Transaction deleted successfully!");
        else
            System.out.println("Transaction not found.");
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