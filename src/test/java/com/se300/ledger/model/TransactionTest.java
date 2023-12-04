package com.se300.ledger.model;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.TestSmartStoreApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { TestSmartStoreApplication.class })
public class TransactionTest {

    // TODO: Implement Transaction Test

    // void setTransactionId(String transactionId)
    // void setAmount(Integer amount)
    // void setFee(Integer fee)
    // void setNote(String note)
    // void setPayer(Account payer)
    // setReceiver(Account receiver)

    @Test
    void testTransaction() {
        String transactionId = "123";
        Integer amount = 100;
        Integer fee = 2;
        String note = "test";
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Account account = new Account("address3", 0);

        Transaction transaction = new Transaction("000", 0, 0, "none", account, account);

        transaction.setTransactionId(transactionId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setNote(note);
        transaction.setPayer(payer);
        transaction.setReceiver(receiver);

    }

    @Test
    void testEqualsWithEqualObjects() {

        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);

        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id1", 10, 1, "Note", payer, receiver);

        // Act & Assert
        assertTrue(transaction1.equals(transaction2), "Transactions with the same values should be equal");
        assertEquals(transaction1.hashCode(), transaction2.hashCode(), "Hash codes should be equal for equal objects");
    }

    @Test
    void testEqualsWithDifferentObjects() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);

        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id2", 10, 1, "Note", payer, receiver);

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different IDs should not be equal");
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction = new Transaction("id1", 10, 1, "Note", payer, receiver);

        // Act & Assert
        assertFalse(transaction.equals(null), "Transaction should not be equal to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Object otherObject = new Object();

        // Act & Assert
        assertFalse(transaction.equals(otherObject),
                "Transaction should not be equal to an object of a different type");
    }

    @Test
    void testEqualsWithSelf() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction = new Transaction("id1", 10, 1, "Note", payer, receiver);

        // Act & Assert
        assertTrue(transaction.equals(transaction), "Transaction should be equal to itself");
    }

    // Additional test for each field that participates in the equals/hashCode
    // contract
    // Here's one example, and similar tests should be made for amount, fee, note,
    // payer, receiver

    @Test
    void testEqualsDifferentTransactionId() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id2", 10, 1, "Note", payer, receiver);

        // Act & Assert
        assertFalse(transaction1.equals(transaction2),
                "Transactions with different transaction IDs should not be equal");
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode(),
                "Hash codes should not be equal for objects with different transaction IDs");
    }

    @Test
    void testEqualsDifferentAmount() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id1", 20, 1, "Note", payer, receiver); // only amount is different

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different amounts should not be equal");
    }

    @Test
    void testEqualsDifferentFee() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id1", 10, 2, "Note", payer, receiver); // only fee is different

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different fees should not be equal");
    }

    @Test
    void testEqualsDifferentNote() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver = new Account("address2", 100);
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver);
        Transaction transaction2 = new Transaction("id1", 10, 1, "Different Note", payer, receiver); // only note is
                                                                                                     // different

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different notes should not be equal");
    }

    @Test
    void testEqualsDifferentPayer() {
        // Arrange
        Account payer1 = new Account("address1", 100);
        Account payer2 = new Account("address3", 100); // different payer
        Account receiver = new Account("address2", 100);
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer1, receiver);
        Transaction transaction2 = new Transaction("id1", 10, 1, "Note", payer2, receiver); // only payer is different

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different payers should not be equal");
    }

    @Test
    void testEqualsDifferentReceiver() {
        // Arrange
        Account payer = new Account("address1", 100);
        Account receiver1 = new Account("address2", 100);
        Account receiver2 = new Account("address4", 100); // different receiver
        Transaction transaction1 = new Transaction("id1", 10, 1, "Note", payer, receiver1);
        Transaction transaction2 = new Transaction("id1", 10, 1, "Note", payer, receiver2); // only receiver is
                                                                                            // different

        // Act & Assert
        assertFalse(transaction1.equals(transaction2), "Transactions with different receivers should not be equal");
    }


}