package com.expensetracker.model;

import java.util.StringJoiner;

public class Category {
    private int categoryId;
    private int userId;
    private String name;
    private String type;

    public Category(int categoryId, int userId, String name, String type) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("categoryId=" + categoryId)
                .add("userId=" + userId)
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .toString();
    }
}
