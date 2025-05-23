package com.banking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing database connections
 */
public class DatabaseConnection {
    // IMPORTANT: Ensure this matches your actual MySQL database name.
    // Based on your previous SQL commands, it was 'simple_banking_db'.
    // If your database is named 'banking_system', then this is correct.
    // Otherwise, change it to "jdbc:mysql://localhost:3306/simple_banking_db"
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/simple_banking_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password"; // Change this to your actual MySQL root password if it's different

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Register the JDBC driver (optional for modern JDBC, but harmless)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver registered.");

            // Attempt to establish the initial connection here
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Initial database connection established.");

        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found. Make sure 'mysql-connector-j-8.x.x.jar' is in your project's 'Referenced Libraries'.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ERROR: Initial database connection failed. Check your JDBC_URL, USERNAME, and PASSWORD.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Check if the connection is null or closed, then try to re-establish
            if (connection == null || connection.isClosed()) {
                System.out.println("Attempting to re-establish database connection...");
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Database connection re-established.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to get or re-establish database connection.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Error closing database connection.");
            e.printStackTrace();
        }
    }
}
