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

CREATE TABLE IF NOT EXISTS budgets (

    budget_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,

    category_id INT NOT NULL,

    budget_amount DECIMAL(10,2) NOT NULL,

    budget_month DATE NOT NULL,

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,

    FOREIGN KEY (category_id)
    REFERENCES categories(category_id)
    ON DELETE CASCADE,

    UNIQUE(user_id, category_id, budget_month)
);

CREATE TABLE IF NOT EXISTS transaction_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_id INT,
    action_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    old_amount DECIMAL(10,2),
    new_amount DECIMAL(10,2),
    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- INSERT Trigger
DELIMITER $$

DROP TRIGGER IF EXISTS after_transaction_insert$$

CREATE TRIGGER after_transaction_insert
AFTER INSERT
ON transactions
FOR EACH ROW
BEGIN
INSERT INTO transaction_logs(
    transaction_id,
    action_type,
    old_amount,
    new_amount
)
VALUES(
    NEW.transaction_id,
    'INSERT',
    NULL,
    NEW.amount
);
END$$

-- UPDATE Trigger
DROP TRIGGER IF EXISTS after_transaction_update$$

CREATE TRIGGER after_transaction_update
AFTER UPDATE
ON transactions
FOR EACH ROW
BEGIN
INSERT INTO transaction_logs(
    transaction_id,
    action_type,
    old_amount,
    new_amount
)
VALUES(
    NEW.transaction_id,
    'UPDATE',
    OLD.amount,
    NEW.amount
);
END$$

-- DELETE Trigger
DROP TRIGGER IF EXISTS after_transaction_delete$$

CREATE TRIGGER after_transaction_delete
AFTER DELETE
ON transactions
FOR EACH ROW
BEGIN
INSERT INTO transaction_logs(
    transaction_id,
    action_type,
    old_amount,
    new_amount
)
VALUES(
    OLD.transaction_id,
    'DELETE',
    OLD.amount,
    NULL
);
END$$

DELIMITER ;

