package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.model.User;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category addCategory(Category category) {

        User user = userRepository
                .findById(category.getUser().getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        category.setUser(user);

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(Integer userId) {
        return categoryRepository.findByUserUserId(userId);
    }

    public Category getCategory(Integer categoryId, Integer userId) {

        return categoryRepository
                .findById(categoryId)
                .filter(category ->
                        category.getUser()
                                .getUserId()
                                .equals(userId))
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }

    public void deleteCategory(Integer categoryId, Integer userId) {

        Category category = getCategory(categoryId, userId);

        categoryRepository.delete(category);
    }
}