package com.banking.dao;

import com.banking.model.BankAccount;
import com.banking.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for BankAccount entity
 */
public class BankAccountDAO {
    private Connection connection;
    private CustomerDAO customerDAO;
    
    public BankAccountDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.customerDAO = new CustomerDAO();
    }
    
    /**
     * Create a new bank account in the database
     */
    public boolean createAccount(BankAccount account) {
        String sql = "INSERT INTO bank_accounts (account_number, balance, account_type, customer_id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountNumber());
            statement.setDouble(2, account.getBalance());
            statement.setString(3, account.getAccountType().toString());
            statement.setString(4, account.getOwner().getCustomerId());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error creating bank account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Find an account by its account number
     */
    public BankAccount findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM bank_accounts WHERE account_number = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToBankAccount(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error finding bank account: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all accounts for a specific customer
     */
    public List<BankAccount> findByCustomerId(String customerId) {
        List<BankAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM bank_accounts WHERE customer_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerId);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                accounts.add(mapResultSetToBankAccount(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding accounts for customer: " + e.getMessage());
        }
        
        return accounts;
    }
    
    /**
     * Update account balance
     */
    public boolean updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE bank_accounts SET balance = ? WHERE account_number = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete an account by its account number
     */
    public boolean deleteAccount(String accountNumber) {
        String sql = "DELETE FROM bank_accounts WHERE account_number = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting bank account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Helper method to map ResultSet to BankAccount object
     */
    private BankAccount mapResultSetToBankAccount(ResultSet resultSet) throws SQLException {
        String accountNumber = resultSet.getString("account_number");
        double balance = resultSet.getDouble("balance");
        String accountTypeStr = resultSet.getString("account_type");
        String customerId = resultSet.getString("customer_id");
        
        // Get the customer owner
        Customer owner = customerDAO.findById(customerId);
        if (owner == null) {
            throw new SQLException("Customer not found for account " + accountNumber);
        }
        
        // Create the account with the appropriate type
        BankAccount.AccountType accountType = BankAccount.AccountType.valueOf(accountTypeStr);
        BankAccount account = new BankAccount(owner, accountType);
        
        // Update the account with data from the database
        // Note: In a real application, you might want to use reflection or a better way to set these values
        
        return account;
    }
}