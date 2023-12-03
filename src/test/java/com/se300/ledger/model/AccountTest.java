package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.service.LedgerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { TestSmartStoreApplication.class })
public class AccountTest {

    @Test
    void testAccountInstantiation() throws LedgerException {

        Account dummyAccount = new Account("sergey", 0);

        assertAll("Verify Account properties",
                () -> assertEquals("sergey", dummyAccount.getAddress()),
                () -> assertEquals(0, dummyAccount.getBalance()));

    }

    @Test
    void testEquals() {
        // Arrange
        Account account1 = new Account("address1", 100);
        Account account2 = new Account("address1", 100);
        Account account3 = new Account("address2", 200);

        // Act
        boolean result1 = account1.equals(account2);
        boolean result2 = account1.equals(account3);

        // Assert
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void testEqualsSelf() {
        // Arrange
        Account account = new Account("address", 100);

        // Act & Assert
        assertTrue(account.equals(account), "An account should be equal to itself");
    }

    @Test
    void testEqualsNull() {
        // Arrange
        Account account = new Account("address", 100);

        // Act & Assert
        assertFalse(account.equals(null), "An account should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        // Arrange
        Account account = new Account("address", 100);
        Object otherObject = new Object();

        // Act & Assert
        assertFalse(account.equals(otherObject), "An account should not be equal to an object of a different type");
    }

    @Test
    void testEqualsSameAddressDifferentBalance() {
        // Arrange
        Account account1 = new Account("address1", 100);
        Account account2 = new Account("address1", 200);

        // Act & Assert
        assertFalse(account1.equals(account2),
                "An account with the same address but different balance should not be equal");
    }

    @Test
    void testEqualsDifferentAddressSameBalance() {
        // Arrange
        Account account1 = new Account("address1", 100);
        Account account2 = new Account("address2", 100);

        // Act & Assert
        assertFalse(account1.equals(account2),
                "An account with a different address but the same balance should not be equal");
    }

    @Test
    void testHashCode() {
        // Arrange
        Account account1 = new Account("address1", 100);
        Account account2 = new Account("address1", 100);
        Account account3 = new Account("address2", 200);

        // Act
        int hash1 = account1.hashCode();
        int hash2 = account2.hashCode();
        int hash3 = account3.hashCode();

        // Assert
        assertEquals(hash1, hash2, "Hash codes should be equal for equal objects");
        assertNotEquals(hash1, hash3, "Hash codes should not be equal for non-equal objects");
    }

}
