package com.expensetracker;

import com.expensetracker.model.Category;
import com.expensetracker.service.CategoryService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryService categoryService = new CategoryService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> addCategory();

                case 2 -> viewCategories();

                case 3 -> {
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
}