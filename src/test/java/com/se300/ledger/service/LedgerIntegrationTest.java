package com.se300.ledger.service;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.checkerframework.checker.units.qual.m;

@SpringBootTest(classes = { TestSmartStoreApplication.class })
public class LedgerIntegrationTest {

        @Autowired
        private Ledger ledger;

        @Test
        public void testCompleteLedger() throws LedgerException {

                Account master = ledger.getUncommittedBlock().getAccount("master");
                Account mary = ledger.createAccount("mary");

                Transaction firstTransaction = new Transaction("1", 60, 10, "simple test", master, mary);
                Transaction secondTransaction = new Transaction("2", 60, 10, "simple test", master, mary);
                Transaction thirdTransaction = new Transaction("3", 60, 10, "simple test", master, mary);
                Transaction forthTransaction = new Transaction("4", 60, 10, "simple test", master, mary);
                Transaction fifthTransaction = new Transaction("5", 60, 10, "simple test", master, mary);
                Transaction sixTransaction = new Transaction("6", 60, 10, "simple test", master, mary);
                Transaction seventhTransaction = new Transaction("7", 60, 10, "simple test", master, mary);
                Transaction eightsTransaction = new Transaction("8", 60, 10, "simple test", master, mary);
                Transaction ninthTransaction = new Transaction("9", 60, 10, "simple test", master, mary);
                Transaction tenthTransaction = new Transaction("10", 60, 10, "simple test", master, mary);

                ledger.processTransaction(firstTransaction);
                ledger.processTransaction(secondTransaction);
                ledger.processTransaction(thirdTransaction);
                ledger.processTransaction(forthTransaction);
                ledger.processTransaction(fifthTransaction);
                ledger.processTransaction(sixTransaction);
                ledger.processTransaction(seventhTransaction);
                ledger.processTransaction(eightsTransaction);
                ledger.processTransaction(ninthTransaction);
                ledger.processTransaction(tenthTransaction);

                assertEquals(600, mary.getBalance());
        }

        // TODO: (done) Create Another Ledger Integration Test
        @Test
        public void testMultipleAccountTransactions() throws LedgerException {
                // Setup - create accounts
                Account alice = ledger.createAccount("alice");
                Account bob = ledger.createAccount("bob");
                Account charlie = ledger.createAccount("charlie");

                alice.setBalance(1000);
                bob.setBalance(1000);
                charlie.setBalance(1000);

                // Process transactions
                Transaction testTransaction = new Transaction("11", 100, 10, "pay charlie", charlie, alice);
                ledger.processTransaction(testTransaction);
                ledger.processTransaction(new Transaction("12", 50, 20, "pay bob", alice, bob));
                ledger.processTransaction(new Transaction("13", 30, 30, "pay charlie", bob, charlie));
                ledger.processTransaction(new Transaction("14", 20, 10, "refund to alice", charlie, alice));

                // Assertions
                assertEquals(1050, alice.getBalance());
                assertEquals(990, bob.getBalance());
                assertEquals(890, charlie.getBalance());

                // Validate that the uncommitted block contains the transactions
                assertEquals(4, ledger.getUncommittedBlock().getTransactionList().size(),
                                "Uncommitted block should contain all transactions.");

                Transaction testTransaction2 = testTransaction;
                assertEquals(testTransaction, testTransaction2);

                assertEquals(testTransaction, ledger.getTransaction("11"),
                                "Transaction should be retrievable by ID.");


        }
}