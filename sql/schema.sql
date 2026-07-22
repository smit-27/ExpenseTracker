-- ===========================================
-- Expense Tracker Database Schema
-- Author: Smit Bangar
-- ===========================================

CREATE DATABASE IF NOT EXISTS expense_tracker;
USE expense_tracker;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,

    name VARCHAR(50) NOT NULL,

    type ENUM('INCOME', 'EXPENSE') NOT NULL,

    CONSTRAINT fk_category_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,

    CONSTRAINT uq_user_category
    UNIQUE (user_id, name)
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,

    category_id INT NOT NULL,

    amount DECIMAL(10,2) NOT NULL,

    description VARCHAR(255),

    transaction_date DATE NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,

    CONSTRAINT fk_transaction_category
    FOREIGN KEY (category_id)
    REFERENCES categories(category_id)
    ON DELETE CASCADE
);

