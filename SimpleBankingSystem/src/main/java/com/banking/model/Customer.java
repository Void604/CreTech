package com.banking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a customer in the banking system
 */
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String username; // Added for login
    private String password; // Added for login (in a real app, this would be a hashed password)
    private List<BankAccount> accounts;

    // Constructor for new customer signup
    public Customer(String firstName, String lastName, String email, String phoneNumber, String address, String username, String password) {
        this.customerId = generateCustomerId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password; // Store plain password for simplicity in this example
        this.accounts = new ArrayList<>();
    }

    // Constructor for loading existing customer from DB (e.g., after login)
    // This constructor includes customerId as it would be retrieved from the database
    public Customer(String customerId, String firstName, String lastName, String email, String phoneNumber, String address, String username, String password) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>(); // Accounts will be loaded separately or lazily
    }

    // Generate a unique customer ID
    private String generateCustomerId() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Add a bank account to this customer
    public void addAccount(BankAccount account) {
        this.accounts.add(account);
    }

    // Get all accounts for this customer
    public List<BankAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    // Set accounts (useful when loading customer data from DB)
    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    // No setter for customerId as it's generated once

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // New Getters and Setters for username and password
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                // Do NOT include password in toString for security reasons in a real app
                '}';
    }
}
