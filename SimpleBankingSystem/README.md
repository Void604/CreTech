# Java Banking System

A simple banking system built with Java and MySQL that demonstrates core banking operations including account management, transactions, and customer management.

## Project Structure

```
src/main/java/com/banking/
├── model/
│   ├── BankAccount.java
│   ├── Customer.java
│   └── Transaction.java
├── dao/
│   ├── BankAccountDAO.java
│   ├── CustomerDAO.java
│   ├── DatabaseConnection.java
│   └── TransactionDAO.java
├── service/
│   └── BankingService.java
└── ui/
    └── BankingConsoleApp.java
```

## Setup Instructions

### Prerequisites

1. Java JDK 11 or higher
2. MySQL Server (5.7+)
3. Maven
4. VS Code with the following extensions:
   - Extension Pack for Java
   - Database Client extension

### Database Setup

1. Open MySQL and run the script located in `database/banking_system_schema.sql` to create the database and tables.

   ```bash
   mysql -u root -p < database/banking_system_schema.sql
   ```

2. Update the database connection settings in `src/main/java/com/banking/dao/DatabaseConnection.java` with your MySQL credentials.

### Project Setup in VS Code

1. Open VS Code
2. Click on "File" > "Open Folder" and select the project folder
3. VS Code should automatically recognize the Maven project
4. Right-click on `pom.xml` and select "Maven: Update Project" to download all dependencies

### Running the Application

1. Right-click on the `BankingConsoleApp.java` file in VS Code
2. Select "Run Java" or use the play button that appears above the `main` method
3. The console application will start in the integrated terminal

## Key Features

### 1. Customer Management
- Register new customers
- View customer details
- Update customer information

### 2. Account Management
- Create different types of accounts (Checking, Savings, Credit)
- View account details and balances
- Close accounts

### 3. Transaction Processing
- Deposit funds
- Withdraw funds
- Transfer between accounts
- View transaction history

## Class Overview

### Model Classes

1. **Customer.java**: Represents a bank customer with personal information.
2. **BankAccount.java**: Represents a bank account with balance and operations.
3. **Transaction.java**: Represents a financial transaction with type, amount, and timestamp.

### Data Access Objects (DAO)

1. **CustomerDAO.java**: Handles database operations for customer data.
2. **BankAccountDAO.java**: Handles database operations for account data.
3. **TransactionDAO.java**: Handles database operations for transaction data.
4. **DatabaseConnection.java**: Manages database connections.

### Service Layer

1. **BankingService.java**: Provides high-level banking operations by coordinating between model and DAO classes.

### User Interface

1. **BankingConsoleApp.java**: Console-based user interface for the banking system.

## Testing

To run the tests, use the following Maven command:

```bash
mvn test
```

## Future Enhancements

- Web-based user interface using Spring Boot
- Authentication and authorization
- Interest calculation for savings accounts
- Credit card and loan features
- Scheduled payments and transfers