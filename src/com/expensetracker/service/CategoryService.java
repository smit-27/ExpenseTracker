package com.expensetracker.service;

import com.expensetracker.dao.CategoryDAO;
import com.expensetracker.model.Category;

import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public boolean addCategory(Category category) {
        return categoryDAO.addCategory(category);
    }

    public List<Category> getAllCategories(int userId) {
        return categoryDAO.getAllCategories(userId);
    }
}