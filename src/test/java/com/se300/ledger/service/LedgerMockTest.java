package com.se300.ledger.service;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Block;
import com.se300.ledger.repository.AccountRepository;
import com.se300.ledger.repository.TransactionRepository;
import com.se300.ledger.model.Transaction;
import com.se300.ledger.service.Ledger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.util.Map;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = { TestSmartStoreApplication.class })
public class LedgerMockTest {

    @Autowired
    private Ledger ledger;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void testGetAllAccount() throws LedgerException {
        List<Account> list = new ArrayList<Account>();
        Account dummyPayer = new Account("payer", 0);
        Account dummyPayee = new Account("payee", 0);
        list.add(dummyPayer);
        list.add(dummyPayee);

        given(accountRepository.save(any(Account.class))).willReturn(dummyPayee);
        ledger.createAccount(dummyPayee.getAddress());
        ledger.createAccount(dummyPayer.getAddress());
        verify(accountRepository, times(1)).save(dummyPayee);

        Collection<Account> accountList = ledger.getUncommittedBlock().getAccountBalanceMap().values();

        List<Account> differences = list.stream()
                .filter(element -> !accountList.contains(element))
                .collect(Collectors.toList());
        assertEquals(0, differences.size());
    }

    // TODO: (done?) Create Another Ledger Mock Test
    
    @Test
    public void testGetName() {
        String expectedName = "Test Ledger";
        String expectedDescription = "Test Description";
        String exSeed = "Test Seed";

        ledger.setName(expectedName);
        ledger.setDescription(expectedDescription);
        ledger.setSeed(exSeed);

        assertEquals(expectedName, ledger.getName());
        assertEquals(expectedDescription, ledger.getDescription());
        assertEquals(exSeed, ledger.getSeed());

    }

    Transaction transaction = new Transaction();

    @Test
    public void testProcessTransactionAmountNegative() {
        Transaction transaction = new Transaction("id", -1, 10, "test note", new Account("payer", 100),
                new Account("receiver", 100));
        assertThrows(LedgerException.class, () -> ledger.processTransaction(transaction),
                "Transaction Amount Is Out Of Range");
    }

    @Test
    public void testProcessTransactionFeeTooLow() {
        Transaction transaction = new Transaction("id", 100, 9, "test note", new Account("payer", 100),
                new Account("receiver", 100));
        assertThrows(LedgerException.class, () -> ledger.processTransaction(transaction),
                "Transaction Fee Must Be Greater Than 10");
    }

    @Test
    public void testProcessTransactionNoteTooLong() {
        Transaction transaction = new Transaction("id", 100, 10, "a".repeat(1025), new Account("payer", 100),
                new Account("receiver", 100));
        assertThrows(LedgerException.class, () -> ledger.processTransaction(transaction),
                "Note Length Must Be Less Than 1024 Chars");
    }

    @Test
    public void testGetAccountBalancesWhenBlockMapIsEmpty() {
        assertNull(ledger.getAccountBalances());
    }



    @Test
    public void testGetBlockThrowsExceptionForNonexistentBlock() {
        Integer nonExistentBlockNumber = 999;
        assertThrows(LedgerException.class, () -> ledger.getBlock(nonExistentBlockNumber));
    }

    @Test
    public void testGetAccountBalanceWithNoCommittedBlock() {
        String address = "nonexistent";
        assertThrows(LedgerException.class, () -> ledger.getAccountBalance(address),
            "Account Is Not Committed to a Block");
    }

    @Test
public void testGetAccountBalanceForNonExistentAccount() {
    String address = "nonexistent";
    // Assuming there are committed blocks, but no account with the given address
    assertThrows(LedgerException.class, () -> ledger.getAccountBalance(address),
        "Account Does Not Exist");
}

@Test
public void testGetNonExistentBlock() {
    Integer nonExistentBlockNumber = 999;
    assertThrows(LedgerException.class, () -> ledger.getBlock(nonExistentBlockNumber),
        "Block Does Not Exist");
}

@Test
public void testGetNumberOfBlocks() throws NoSuchFieldException, IllegalAccessException {
    // Use reflection to access the private field
    Field blockMapField = Ledger.class.getDeclaredField("blockMap");
    blockMapField.setAccessible(true);

    // Create and populate a new TreeMap
    TreeMap<Integer, Block> testBlockMap = new TreeMap<>();
    testBlockMap.put(1, new Block(1, "hash1"));
    testBlockMap.put(2, new Block(2, "hash2"));

    // Set the blockMap in ledger instance
    blockMapField.set(ledger, testBlockMap);

    // Now, the ledger should have 2 blocks
    assertEquals(2, ledger.getNumberOfBlocks());
}


@Test
public void testGetAccountBalances() throws NoSuchFieldException, IllegalAccessException {
    // Set up blockMap with a block containing accounts
    Field blockMapField = Ledger.class.getDeclaredField("blockMap");
    blockMapField.setAccessible(true);

    TreeMap<Integer, Block> testBlockMap = new TreeMap<>();
    Block block = new Block(1, "hash1");
    Account account1 = new Account("address1", 100);
    Account account2 = new Account("address2", 200);
    block.addAccount(account1.getAddress(), account1);
    block.addAccount(account2.getAddress(), account2);
    testBlockMap.put(1, block);

    blockMapField.set(ledger, testBlockMap);

    // Execute the method and verify the result
    Map<String, Integer> balances = ledger.getAccountBalances();
    assertNotNull(balances);
    assertEquals(2, balances.size());
    assertEquals(100, (int) balances.get("address1"));
    assertEquals(200, (int) balances.get("address2"));
}

@Test
public void testValidateForHashInconsistency() throws NoSuchFieldException, IllegalAccessException {
    // Setup blockMap with blocks having inconsistent hashes
    Field blockMapField = Ledger.class.getDeclaredField("blockMap");
    blockMapField.setAccessible(true);

    TreeMap<Integer, Block> testBlockMap = new TreeMap<>();
    Block block1 = new Block(1, "hash1");
    Block block2 = new Block(2, "hash2");
    block2.setPreviousBlock(block1);
    block2.setPreviousHash("incorrectHash"); // Set a different previous hash to create inconsistency

    testBlockMap.put(1, block1);
    testBlockMap.put(2, block2);

    blockMapField.set(ledger, testBlockMap);

    // Validate should throw LedgerException due to hash inconsistency
    assertThrows(LedgerException.class, () -> ledger.validate());
}

@Test
public void testValidateForIncorrectTransactionCount() throws NoSuchFieldException, IllegalAccessException {
    // Setup blockMap with blocks having incorrect transaction counts
    Field blockMapField = Ledger.class.getDeclaredField("blockMap");
    blockMapField.setAccessible(true);

    TreeMap<Integer, Block> testBlockMap = new TreeMap<>();
    Block block = new Block(1, "hash1");
    // Add fewer or more than 10 transactions to the block
    // Example: block.getTransactionList().add(new Transaction(...));

    testBlockMap.put(1, block);

    blockMapField.set(ledger, testBlockMap);

    // Validate should throw LedgerException due to incorrect transaction count
    assertThrows(LedgerException.class, () -> ledger.validate());
}

@Test
public void testValidateForTotalBalanceAndFeesMismatch() throws NoSuchFieldException, IllegalAccessException {
    // Setup blockMap with blocks, accounts, and transactions
    Field blockMapField = Ledger.class.getDeclaredField("blockMap");
    blockMapField.setAccessible(true);

    TreeMap<Integer, Block> testBlockMap = new TreeMap<>();
    Block block = new Block(1, "hash1");
    Account account = new Account("address", 100);
    block.addAccount(account.getAddress(), account);
    // Add transactions with fees to the block
    // Example: block.getTransactionList().add(new Transaction(...));

    testBlockMap.put(1, block);

    blockMapField.set(ledger, testBlockMap);

    // Validate should throw LedgerException due to total balance and fees mismatch
    assertThrows(LedgerException.class, () -> ledger.validate());
}


}
