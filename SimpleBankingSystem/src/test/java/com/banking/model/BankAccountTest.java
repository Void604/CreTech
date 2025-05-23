package com.banking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BankAccount
 */
public class BankAccountTest {
    private Customer testCustomer;
    private BankAccount checkingAccount;
    
    @BeforeEach
    public void setUp() {
        testCustomer = new Customer("John", "Doe", "john@example.com", "555-123-4567", "123 Main St");
        checkingAccount = new BankAccount(testCustomer, BankAccount.AccountType.CHECKING);
    }
    
    @Test
    public void testNewAccountHasZeroBalance() {
        assertEquals(0.0, checkingAccount.getBalance(), "New account should have zero balance");
    }
    
    @Test
    public void testDeposit() {
        assertTrue(checkingAccount.deposit(100.0), "Deposit should succeed");
        assertEquals(100.0, checkingAccount.getBalance(), "Balance should be updated after deposit");
    }
    
    @Test
    public void testDepositNegativeAmount() {
        assertFalse(checkingAccount.deposit(-50.0), "Negative deposit should fail");
        assertEquals(0.0, checkingAccount.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    public void testWithdraw() {
        checkingAccount.deposit(100.0);
        assertTrue(checkingAccount.withdraw(50.0), "Withdrawal should succeed");
        assertEquals(50.0, checkingAccount.getBalance(), "Balance should be updated after withdrawal");
    }
    
    @Test
    public void testWithdrawNegativeAmount() {
        checkingAccount.deposit(100.0);
        assertFalse(checkingAccount.withdraw(-50.0), "Negative withdrawal should fail");
        assertEquals(100.0, checkingAccount.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    public void testWithdrawInsufficientFunds() {
        checkingAccount.deposit(100.0);
        assertFalse(checkingAccount.withdraw(150.0), "Withdrawal exceeding balance should fail");
        assertEquals(100.0, checkingAccount.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    public void testTransactionRecording() {
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(50.0);
        
        assertEquals(2, checkingAccount.getTransactionHistory().size(), "Should have recorded two transactions");
        
        Transaction depositTransaction = checkingAccount.getTransactionHistory().get(0);
        Transaction withdrawalTransaction = checkingAccount.getTransactionHistory().get(1);
        
        assertEquals(Transaction.TransactionType.DEPOSIT, depositTransaction.getType(), "First transaction should be a deposit");
        assertEquals(100.0, depositTransaction.getAmount(), "Deposit amount should be recorded correctly");
        
        assertEquals(Transaction.TransactionType.WITHDRAWAL, withdrawalTransaction.getType(), "Second transaction should be a withdrawal");
        assertEquals(50.0, withdrawalTransaction.getAmount(), "Withdrawal amount should be recorded correctly");
    }
}