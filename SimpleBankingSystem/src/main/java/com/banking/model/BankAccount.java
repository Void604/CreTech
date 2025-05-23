package com.banking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a bank account in the banking system
 */
public class BankAccount {
    private String accountNumber;
    private double balance;
    private AccountType accountType;
    private Customer owner;
    private List<Transaction> transactions;

    public enum AccountType {
        CHECKING,
        SAVINGS,
        CREDIT
    }

    // Constructor
    public BankAccount(Customer owner, AccountType accountType) {
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.accountType = accountType;
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    // Generate a unique account number
    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Deposit money into the account
    public synchronized boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        this.balance += amount;
        this.transactions.add(new Transaction(
            Transaction.TransactionType.DEPOSIT, 
            amount, 
            "Deposit to account " + this.accountNumber
        ));
        return true;
    }

    // Withdraw money from the account
    public synchronized boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        
        this.balance -= amount;
        this.transactions.add(new Transaction(
            Transaction.TransactionType.WITHDRAWAL, 
            amount, 
            "Withdrawal from account " + this.accountNumber
        ));
        return true;
    }

    // Get current balance
    public double getBalance() {
        return this.balance;
    }

    // Get account transaction history
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Customer getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", owner=" + owner.getFirstName() + " " + owner.getLastName() +
                '}';
    }
}