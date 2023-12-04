package com.se300.ledger.service;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.repository.AccountRepository;
import com.se300.ledger.model.Transaction;
import com.se300.ledger.service.Ledger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    // TODO: (done) Create Another Ledger Mock Test
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

    // @Test
    // public void testProcessTransaction() throws LedgerException {
    //     List<Account> list = new ArrayList<Account>();
    //     Account dummyPayer = new Account("payer", 200);
    //     Account dummyPayee = new Account("payee", 300);
    //     list.add(dummyPayer);
    //     list.add(dummyPayee);

    //     Transaction testTransaction = new Transaction("id1", 90, 10, "test note", dummyPayer, dummyPayee);
        
    //     ledger.processTransaction(testTransaction);
        
    //     verify(accountRepository, times(1)).save(dummyPayer);
    //     verify(accountRepository, times(1)).save(dummyPayee);
    //     assertEquals(110, dummyPayer.getBalance());
    //     assertEquals(390, dummyPayee.getBalance());

    // }

}

