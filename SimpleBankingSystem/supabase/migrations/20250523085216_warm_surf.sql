-- MySQL Schema for Banking System

-- Drop database if it exists and create a new one
DROP DATABASE IF EXISTS banking_system;
CREATE DATABASE banking_system;
USE banking_system;

-- Customers table
CREATE TABLE customers (
    customer_id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bank accounts table
CREATE TABLE bank_accounts (
    account_number VARCHAR(36) PRIMARY KEY,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    account_type ENUM('CHECKING', 'SAVINGS', 'CREDIT') NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id VARCHAR(36) PRIMARY KEY,
    type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description VARCHAR(255),
    timestamp TIMESTAMP NOT NULL,
    account_number VARCHAR(36) NOT NULL,
    FOREIGN KEY (account_number) REFERENCES bank_accounts(account_number)
);

-- Create indexes for better performance
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_bank_accounts_customer ON bank_accounts(customer_id);
CREATE INDEX idx_transactions_account ON transactions(account_number);
CREATE INDEX idx_transactions_timestamp ON transactions(timestamp);

-- Sample data (optional)
INSERT INTO customers (customer_id, first_name, last_name, email, phone_number, address)
VALUES ('CUST-12345678', 'John', 'Doe', 'john@example.com', '555-123-4567', '123 Main St, Anytown, USA');

INSERT INTO bank_accounts (account_number, balance, account_type, customer_id)
VALUES ('ACC-87654321', 1000.00, 'CHECKING', 'CUST-12345678');

INSERT INTO transactions (transaction_id, type, amount, description, timestamp, account_number)
VALUES ('TXN-11111111', 'DEPOSIT', 1000.00, 'Initial deposit', CURRENT_TIMESTAMP, 'ACC-87654321');