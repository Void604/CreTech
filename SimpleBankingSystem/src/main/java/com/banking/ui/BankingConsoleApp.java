package com.banking.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.banking.model.BankAccount;
import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.service.BankingService;

/**
 * Simple console application to demonstrate banking functionality with login and signup.
 */
public class BankingConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BankingService bankingService = new BankingService();
    private static Customer currentCustomer = null; // Stores the currently logged-in customer

    public static void main(String[] args) {
        System.out.println("Welcome to the Banking System");

        boolean running = true;
        while (running) {
            if (currentCustomer == null) {
                displayMainMenu();
            } else {
                displayCustomerMenu();
            }

            int choice = getIntInput("Enter your choice: ");

            if (currentCustomer == null) {
                running = handleMainMenuChoice(choice);
            } else {
                running = handleCustomerMenuChoice(choice);
            }
        }

        System.out.println("Thank you for using the Banking System. Goodbye!");
        scanner.close(); // Close the scanner when the application exits
    }

    private static void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Login (Username & Password)"); // Updated login option
        System.out.println("2. Register New Customer");
        System.out.println("3. Exit");
    }

    private static void displayCustomerMenu() {
        System.out.println("\n===== CUSTOMER MENU =====");
        System.out.println("Welcome, " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " (ID: " + currentCustomer.getCustomerId() + ")");
        System.out.println("1. View Accounts");
        System.out.println("2. Create New Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. View Transaction History");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
    }

    private static boolean handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                login();
                return true;
            case 2:
                registerCustomer();
                return true;
            case 3:
                return false; // Exit the application
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private static boolean handleCustomerMenuChoice(int choice) {
        switch (choice) {
            case 1:
                viewAccounts();
                return true;
            case 2:
                createAccount();
                return true;
            case 3:
                deposit();
                return true;
            case 4:
                withdraw();
                return true;
            case 5:
                transfer();
                return true;
            case 6:
                viewTransactionHistory();
                return true;
            case 7:
                currentCustomer = null; // Logout the current customer
                System.out.println("Logged out successfully.");
                return true;
            case 8:
                return false; // Exit the application
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    /**
     * Handles the login process, prompting for username and password.
     */
    private static void login() {
        System.out.println("\n===== LOGIN =====");
        String username = getStringInput("Enter your username: ");
        String password = getStringInput("Enter your password: ");

        Customer customer = bankingService.login(username, password);
        if (customer != null) {
            currentCustomer = customer;
            System.out.println("Login successful! Welcome, " + currentCustomer.getFirstName() + ".");
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }

    /**
     * Handles the customer registration process, prompting for all necessary details including username and password.
     */
    private static void registerCustomer() {
        System.out.println("\n===== REGISTER NEW CUSTOMER =====");
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        String email = getStringInput("Enter email: ");
        String phoneNumber = getStringInput("Enter phone number: ");
        String address = getStringInput("Enter address: ");
        String username = getStringInput("Enter desired username: ");
        String password = getStringInput("Enter desired password: ");

        boolean success = bankingService.registerCustomer(firstName, lastName, email, phoneNumber, address, username, password);
        if (success) {
            System.out.println("Registration successful! You can now login with your new username and password.");
            // Automatically log in the newly registered user
            currentCustomer = bankingService.login(username, password);
        } else {
            System.out.println("Registration failed. Please try again with different details.");
        }
    }

    private static void viewAccounts() {
        System.out.println("\n===== YOUR ACCOUNTS =====");
        if (currentCustomer == null) {
            System.out.println("Please login to view accounts.");
            return;
        }
        List<BankAccount> accounts = bankingService.getCustomerAccounts(currentCustomer.getCustomerId());

        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts yet. Create one from the menu.");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: $" + String.format("%.2f", account.getBalance()));
            System.out.println("---------------------------");
        }
    }

    private static void createAccount() {
        System.out.println("\n===== CREATE NEW ACCOUNT =====");
        if (currentCustomer == null) {
            System.out.println("Please login to create an account.");
            return;
        }

        System.out.println("Account Types:");
        System.out.println("1. CHECKING");
        System.out.println("2. SAVINGS");
        System.out.println("3. CREDIT");

        int typeChoice = getIntInput("Choose account type (1-3): ");
        BankAccount.AccountType accountType;

        switch (typeChoice) {
            case 1:
                accountType = BankAccount.AccountType.CHECKING;
                break;
            case 2:
                accountType = BankAccount.AccountType.SAVINGS;
                break;
            case 3:
                accountType = BankAccount.AccountType.CREDIT;
                break;
            default:
                System.out.println("Invalid choice. Setting to CHECKING by default.");
                accountType = BankAccount.AccountType.CHECKING;
        }

        BankAccount account = bankingService.createAccount(currentCustomer.getCustomerId(), accountType);
        if (account != null) {
            System.out.println("Account created successfully!");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
        } else {
            System.out.println("Failed to create account.");
        }
    }

    private static void deposit() {
        System.out.println("\n===== DEPOSIT MONEY =====");
        if (currentCustomer == null) {
            System.out.println("Please login to deposit money.");
            return;
        }
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter amount to deposit: $");

        boolean success = bankingService.deposit(accountNumber, amount);
        if (success) {
            // Message is already printed by BankingService
        } else {
            System.out.println("Deposit operation failed.");
        }
    }

    private static void withdraw() {
        System.out.println("\n===== WITHDRAW MONEY =====");
        if (currentCustomer == null) {
            System.out.println("Please login to withdraw money.");
            return;
        }
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter amount to withdraw: $");

        boolean success = bankingService.withdraw(accountNumber, amount);
        if (success) {
            // Message is already printed by BankingService
        } else {
            System.out.println("Withdrawal operation failed.");
        }
    }

    private static void transfer() {
        System.out.println("\n===== TRANSFER MONEY =====");
        if (currentCustomer == null) {
            System.out.println("Please login to transfer money.");
            return;
        }
        String fromAccountNumber = getStringInput("Enter source account number: ");
        String toAccountNumber = getStringInput("Enter destination account number: ");
        double amount = getDoubleInput("Enter amount to transfer: $");

        boolean success = bankingService.transfer(fromAccountNumber, toAccountNumber, amount);
        if (success) {
            // Message is already printed by BankingService
        } else {
            System.out.println("Transfer operation failed.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");
        if (currentCustomer == null) {
            System.out.println("Please login to view transaction history.");
            return;
        }
        String accountNumber = getStringInput("Enter account number: ");

        System.out.println("1. View all transactions");
        System.out.println("2. View transactions by date range");
        int choice = getIntInput("Enter your choice: ");

        List<Transaction> transactions;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (choice == 1) {
            transactions = bankingService.getTransactionHistory(accountNumber);
        } else if (choice == 2) {
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            boolean dateInputValid = false;
            while (!dateInputValid) {
                try {
                    String startDateStr = getStringInput("Enter start date (YYYY-MM-DD): ");
                    startDate = LocalDateTime.parse(startDateStr + "T00:00:00");
                    String endDateStr = getStringInput("Enter end date (YYYY-MM-DD): ");
                    endDate = LocalDateTime.parse(endDateStr + "T23:59:59");
                    dateInputValid = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                }
            }
            transactions = bankingService.getTransactionHistory(accountNumber, startDate, endDate);
        } else {
            System.out.println("Invalid choice. Showing all transactions.");
            transactions = bankingService.getTransactionHistory(accountNumber);
        }

        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions found for this account or within the specified range.");
            return;
        }

        System.out.println("Transaction History for Account " + accountNumber);
        System.out.println("---------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-10s | %-20s | %s\n", "Type", "Amount", "Date", "Time", "Description");
        System.out.println("---------------------------------------------------");

        for (Transaction transaction : transactions) {
            String formattedTimestamp = transaction.getFormattedTimestamp();
            String[] dateTimeParts = formattedTimestamp.split(" ");

            System.out.printf("%-10s | $%-11.2f | %-10s | %-20s | %s\n",
                    transaction.getType(),
                    transaction.getAmount(),
                    dateTimeParts[0],
                    dateTimeParts[1],
                    transaction.getDescription());
        }
    }

    // Utility methods for input handling
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
