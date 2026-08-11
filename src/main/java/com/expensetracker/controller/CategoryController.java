package com.expensetracker.controller;

import com.expensetracker.model.Category;
import com.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
            @Valid @RequestBody Category category,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        Category savedCategory =
                categoryService.addCategory(
                        category,
                        userId.intValue()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        return ResponseEntity.ok(
                categoryService.getAllCategories(
                        userId.intValue()
                )
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(
            @PathVariable Integer categoryId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        return ResponseEntity.ok(
                categoryService.getCategory(
                        categoryId,
                        userId.intValue()
                )
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer categoryId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");

        categoryService.deleteCategory(
                categoryId,
                userId.intValue()
        );

        return ResponseEntity.noContent().build();
    }
}