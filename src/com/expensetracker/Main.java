package com.expensetracker;

import com.expensetracker.dao.CategoryDAO;
import com.expensetracker.model.Category;

public class Main {

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDAO();

        Category category = new Category(
                0,
                1,
                "Food",
                "EXPENSE"
        );

        boolean success = categoryDAO.addCategory(category);

        if (success) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category.");
        }
    }
}