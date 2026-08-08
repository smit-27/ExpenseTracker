package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(Integer userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Category getCategory(Integer categoryId, Integer userId) {

        return categoryRepository
                .findById(categoryId)
                .filter(category -> category.getUserId().equals(userId))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }

    public void deleteCategory(Integer categoryId, Integer userId) {

        Category category = getCategory(categoryId, userId);

        categoryRepository.delete(category);
    }
}