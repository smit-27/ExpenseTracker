package com.expensetracker.controller;

import com.expensetracker.model.Category;
import com.expensetracker.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(
            @RequestBody Category category) {

        Category savedCategory =
                categoryService.addCategory(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                categoryService.getAllCategories(userId)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(
            @PathVariable Integer categoryId,
            @RequestParam Integer userId) {

        return ResponseEntity.ok(
                categoryService.getCategory(categoryId, userId)
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer categoryId,
            @RequestParam Integer userId) {

        categoryService.deleteCategory(categoryId, userId);

        return ResponseEntity.noContent().build();
    }
}