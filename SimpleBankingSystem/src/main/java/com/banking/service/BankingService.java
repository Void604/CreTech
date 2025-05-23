package com.banking.service;

import java.time.LocalDateTime;
import java.util.List;

import com.banking.dao.BankAccountDAO;
import com.banking.dao.CustomerDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.BankAccount;
import com.banking.model.Customer;
import com.banking.model.Transaction;

/**
 * Service class to handle banking operations, including new login and signup features.
 */
public class BankingService {
    private CustomerDAO customerDAO;
    private BankAccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public BankingService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new BankAccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    /**
     * Register a new customer with a username and password.
     * @param firstName Customer's first name.
     * @param lastName Customer's last name.
     * @param email Customer's email address.
     * @param phoneNumber Customer's phone number.
     * @param address Customer's address.
     * @param username Desired username for login.
     * @param password Desired password for login.
     * @return true if registration is successful, false otherwise (e.g., email or username already exists).
     */
    public boolean registerCustomer(String firstName, String lastName, String email, String phoneNumber, String address, String username, String password) {
        // Check if email is already registered
        if (customerDAO.findByEmail(email) != null) {
            System.out.println("Registration failed: Email already registered.");
            return false;
        }
        // Check if username is already taken
        if (customerDAO.findByUsername(username) != null) {
            System.out.println("Registration failed: Username already taken. Please choose a different username.");
            return false;
        }

        // In a real application, you would hash the password before storing it.
        // For simplicity, we are storing it as plain text for now.
        Customer customer = new Customer(firstName, lastName, email, phoneNumber, address, username, password);
        boolean success = customerDAO.createCustomer(customer);
        if (success) {
            System.out.println("Customer registered successfully with username: " + username);
        } else {
            System.out.println("Failed to register customer.");
        }
        return success;
    }

    /**
     * Authenticate a customer based on username and password.
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return The Customer object if authentication is successful, null otherwise.
     */
    public Customer login(String username, String password) {
        Customer customer = customerDAO.findByUsername(username);
        if (customer != null) {
            // In a real application, you would compare the provided password with the hashed password.
            // For simplicity, we are doing a direct string comparison.
            if (customer.getPassword().equals(password)) {
                System.out.println("Login successful for user: " + username);
                return customer;
            } else {
                System.out.println("Login failed: Incorrect password.");
            }
        } else {
            System.out.println("Login failed: Username not found.");
        }
        return null;
    }

    /**
     * Create a new bank account for a customer
     * @param customerId The ID of the customer for whom to create the account.
     * @param accountType The type of account (CHECKING, SAVINGS, CREDIT).
     * @return The created BankAccount object, or null if creation failed.
     */
    public BankAccount createAccount(String customerId, BankAccount.AccountType accountType) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return null;
        }

        BankAccount account = new BankAccount(customer, accountType);
        boolean success = accountDAO.createAccount(account);

        if (success) {
            // In a real application, you might reload the customer object or manage
            // accounts list more robustly, but for this console app, adding to the
            // in-memory customer object is sufficient for immediate use.
            customer.addAccount(account);
            System.out.println("Account " + account.getAccountNumber() + " created successfully for customer " + customer.getFirstName());
            return account;
        }

        System.out.println("Failed to create account for customer " + customerId);
        return null;
    }

    /**
     * Deposit money into an account
     * @param accountNumber The account number to deposit into.
     * @param amount The amount to deposit.
     * @return true if deposit is successful, false otherwise.
     */
    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Amount must be positive.");
            return false;
        }

        BankAccount account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found for deposit.");
            return false;
        }

        boolean success = account.deposit(amount);
        if (success) {
            accountDAO.updateBalance(accountNumber, account.getBalance());
            Transaction transaction = new Transaction(
                Transaction.TransactionType.DEPOSIT,
                amount,
                "Deposit to account " + accountNumber
            );
            transactionDAO.createTransaction(transaction, accountNumber);
            System.out.println("Successfully deposited $" + String.format("%.2f", amount) + " to account " + accountNumber);
        } else {
            System.out.println("Deposit failed for account " + accountNumber);
        }
        return success;
    }

    /**
     * Withdraw money from an account
     * @param accountNumber The account number to withdraw from.
     * @param amount The amount to withdraw.
     * @return true if withdrawal is successful, false otherwise.
     */
    public boolean withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Amount must be positive.");
            return false;
        }

        BankAccount account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found for withdrawal.");
            return false;
        }

        boolean success = account.withdraw(amount);
        if (success) {
            accountDAO.updateBalance(accountNumber, account.getBalance());
            Transaction transaction = new Transaction(
                Transaction.TransactionType.WITHDRAWAL,
                amount,
                "Withdrawal from account " + accountNumber
            );
            transactionDAO.createTransaction(transaction, accountNumber);
            System.out.println("Successfully withdrew $" + String.format("%.2f", amount) + " from account " + accountNumber);
        } else {
            System.out.println("Withdrawal failed. Insufficient funds or invalid amount for account " + accountNumber);
        }
        return success;
    }

    /**
     * Transfer money between accounts
     * @param fromAccountNumber The source account number.
     * @param toAccountNumber The destination account number.
     * @param amount The amount to transfer.
     * @return true if transfer is successful, false otherwise.
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid transfer amount. Amount must be positive.");
            return false;
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            System.out.println("Cannot transfer to the same account.");
            return false;
        }

        BankAccount fromAccount = accountDAO.findByAccountNumber(fromAccountNumber);
        BankAccount toAccount = accountDAO.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found for transfer.");
            return false;
        }

        if (fromAccount.getBalance() < amount) {
            System.out.println("Insufficient funds for transfer from account " + fromAccountNumber);
            return false;
        }

        // Perform the withdrawal and deposit in a transactional manner (ideally)
        // For simplicity, we'll do sequential updates. In a real system, use DB transactions.
        boolean withdrawSuccess = fromAccount.withdraw(amount);
        if (withdrawSuccess) {
            boolean depositSuccess = toAccount.deposit(amount);
            if (depositSuccess) {
                accountDAO.updateBalance(fromAccountNumber, fromAccount.getBalance());
                accountDAO.updateBalance(toAccountNumber, toAccount.getBalance());

                Transaction withdrawalTransaction = new Transaction(
                    Transaction.TransactionType.TRANSFER,
                    amount,
                    "Transfer to account " + toAccountNumber
                );
                Transaction depositTransaction = new Transaction(
                    Transaction.TransactionType.TRANSFER,
                    amount,
                    "Transfer from account " + fromAccountNumber
                );

                transactionDAO.createTransaction(withdrawalTransaction, fromAccountNumber);
                transactionDAO.createTransaction(depositTransaction, toAccountNumber);
                System.out.println("Successfully transferred $" + String.format("%.2f", amount) + " from " + fromAccountNumber + " to " + toAccountNumber);
                return true;
            } else {
                // Roll back the withdrawal if deposit fails
                fromAccount.deposit(amount);
                System.out.println("Transfer failed: Could not deposit into destination account. Withdrawal rolled back.");
                accountDAO.updateBalance(fromAccountNumber, fromAccount.getBalance()); // Update rolled back balance
            }
        } else {
            System.out.println("Transfer failed: Could not withdraw from source account.");
        }
        return false;
    }

    /**
     * Get account balance
     * @param accountNumber The account number to get the balance for.
     * @return The balance of the account, or -1 if the account is not found.
     */
    public double getBalance(String accountNumber) {
        BankAccount account = accountDAO.findByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return -1;
        }
        return account.getBalance();
    }

    /**
     * Get transaction history for an account
     * @param accountNumber The account number to retrieve transactions for.
     * @return A list of Transaction objects for the given account.
     */
    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionDAO.findByAccountNumber(accountNumber);
    }

    /**
     * Get transaction history for an account within a date range
     * @param accountNumber The account number.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return A list of Transaction objects within the specified date range.
     */
    public List<Transaction> getTransactionHistory(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionDAO.findByAccountAndDateRange(accountNumber, startDate, endDate);
    }

    /**
     * Get all accounts for a customer
     * @param customerId The ID of the customer.
     * @return A list of BankAccount objects associated with the customer.
     */
    public List<BankAccount> getCustomerAccounts(String customerId) {
        return accountDAO.findByCustomerId(customerId);
    }

    /**
     * Get customer details by ID
     * @param customerId The ID of the customer.
     * @return The Customer object if found, null otherwise.
     */
    public Customer getCustomerById(String customerId) {
        return customerDAO.findById(customerId);
    }

    /**
     * Get customer details by email
     * @param email The email of the customer.
     * @return The Customer object if found, null otherwise.
     */
    public Customer getCustomerByEmail(String email) {
        return customerDAO.findByEmail(email);
    }
}
