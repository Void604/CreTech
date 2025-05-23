package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.banking.model.Customer;

/**
 * Data Access Object for Customer entity
 */
public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
        // Ensure DatabaseConnection.getInstance() and getConnection() are properly implemented
        // to return a valid database connection.
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Create a new customer in the database
     * This method now includes username and password for signup.
     * @param customer The Customer object to create.
     * @return true if the customer was created successfully, false otherwise.
     */
    public boolean createCustomer(Customer customer) {
        // Updated SQL to include username and password columns
        String sql = "INSERT INTO customers (customer_id, first_name, last_name, email, phone_number, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getCustomerId());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getPhoneNumber());
            statement.setString(6, customer.getAddress());
            statement.setString(7, customer.getUsername()); // Set username
            statement.setString(8, customer.getPassword()); // Set password (or hashed password)

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            // Log the full stack trace for better debugging
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Find a customer by their ID
     * @param customerId The ID of the customer to find.
     * @return The Customer object if found, null otherwise.
     */
    public Customer findById(String customerId) {
        String sql = "SELECT customer_id, first_name, last_name, email, phone_number, address, username, password FROM customers WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error finding customer by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find a customer by their username. This is crucial for login functionality.
     * @param username The username of the customer to find.
     * @return The Customer object if found, null otherwise.
     */
    public Customer findByUsername(String username) {
        String sql = "SELECT customer_id, first_name, last_name, email, phone_number, address, username, password FROM customers WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error finding customer by username: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find a customer by their email
     * @param email The email of the customer to find.
     * @return The Customer object if found, null otherwise.
     */
    public Customer findByEmail(String email) {
        String sql = "SELECT customer_id, first_name, last_name, email, phone_number, address, username, password FROM customers WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error finding customer by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all customers from the database
     * @return A list of all Customer objects.
     */
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, email, phone_number, address, username, password FROM customers";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                customers.add(mapResultSetToCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Update an existing customer
     * This method now includes username and password for updates.
     * @param customer The Customer object to update.
     * @return true if the customer was updated successfully, false otherwise.
     */
    public boolean updateCustomer(Customer customer) {
        // Updated SQL to include username and password in the UPDATE statement
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, username = ?, password = ? WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhoneNumber());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getUsername()); // Set username for update
            statement.setString(7, customer.getPassword()); // Set password for update
            statement.setString(8, customer.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a customer by their ID
     * @param customerId The ID of the customer to delete.
     * @return true if the customer was deleted successfully, false otherwise.
     */
    public boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper method to map ResultSet to Customer object.
     * This method now retrieves username and password from the ResultSet.
     * @param resultSet The ResultSet containing customer data.
     * @return A Customer object populated with data from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        String customerId = resultSet.getString("customer_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");
        String address = resultSet.getString("address");
        String username = resultSet.getString("username"); // Retrieve username
        String password = resultSet.getString("password"); // Retrieve password

        // Use the constructor that takes customerId, username, and password
        Customer customer = new Customer(customerId, firstName, lastName, email, phoneNumber, address, username, password);
        // Note: Bank accounts for this customer would typically be loaded separately
        // via BankAccountDAO if needed, to avoid N+1 query issues.
        return customer;
    }
}
