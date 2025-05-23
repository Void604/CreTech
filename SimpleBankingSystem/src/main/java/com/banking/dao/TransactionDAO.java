package com.banking.dao;

import com.banking.model.Transaction;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction entity
 */
public class TransactionDAO {
    private Connection connection;
    
    public TransactionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Create a new transaction in the database
     */
    public boolean createTransaction(Transaction transaction, String accountNumber) {
        String sql = "INSERT INTO transactions (transaction_id, type, amount, description, timestamp, account_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getTransactionId());
            statement.setString(2, transaction.getType().toString());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getDescription());
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            statement.setString(6, accountNumber);
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error creating transaction: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Find a transaction by its ID
     */
    public Transaction findById(String transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transactionId);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTransaction(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error finding transaction: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all transactions for a specific account
     */
    public List<Transaction> findByAccountNumber(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding transactions for account: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Get transactions for an account within a date range
     */
    public List<Transaction> findByAccountAndDateRange(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            statement.setTimestamp(2, Timestamp.valueOf(startDate));
            statement.setTimestamp(3, Timestamp.valueOf(endDate));
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error finding transactions for date range: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Helper method to map ResultSet to Transaction object
     */
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        String transactionId = resultSet.getString("transaction_id");
        String typeStr = resultSet.getString("type");
        double amount = resultSet.getDouble("amount");
        String description = resultSet.getString("description");
        LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
        
        Transaction.TransactionType type = Transaction.TransactionType.valueOf(typeStr);
        
        // Create a new transaction with the data from the database
        Transaction transaction = new Transaction(type, amount, description);
        // Note: In a real application, you might want to use reflection or a better way to set these values
        
        return transaction;
    }
}