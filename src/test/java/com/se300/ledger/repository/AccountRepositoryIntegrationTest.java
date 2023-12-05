package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.se300.ledger.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountRepositoryIntegrationTest {

    //TODO: (done) Implement Account Repository Integration Test
        @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAndRetrieveAccount() {
        Account account = new Account("testAddress", 100);

        accountRepository.save(account);
        Account retrievedAccount = accountRepository.findById("testAddress").orElse(null);

        assertNotNull(retrievedAccount);
        assertEquals("testAddress", retrievedAccount.getAddress());
        assertEquals(100, retrievedAccount.getBalance());
    }

    @Test
    public void testUpdateAccount() {
        Account account = new Account("testAddress", 100);
        accountRepository.save(account);

        Account retrievedAccount = accountRepository.findById("testAddress").orElse(null);
        assertNotNull(retrievedAccount);

        retrievedAccount.setBalance(150);
        accountRepository.save(retrievedAccount);

        Account updatedAccount = accountRepository.findById("testAddress").orElse(null);
        assertNotNull(updatedAccount);
        assertEquals(150, updatedAccount.getBalance());
    }

    @Test
    public void testDeleteAccount() {
        Account account = new Account("testAddress", 100);
        accountRepository.save(account);

        accountRepository.deleteById("testAddress");

        assertFalse(accountRepository.existsById("testAddress"));
    }

}
