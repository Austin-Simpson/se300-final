package com.se300.ledger.repository;

import com.se300.ledger.SmartStoreApplication;
import com.se300.ledger.model.Account;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SmartStoreApplication.class})
public class AccountRepositoryMockTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private FakeAccountRepository fakeAccountRepository;

    @Test
    void testSaveAccount() {
        // Given
        Account account = new Account("testAddress", 100);

        // When
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Account savedAccount = accountRepository.save(account);

        // Then
        assertEquals("testAddress", savedAccount.getAddress());
        assertEquals(100, savedAccount.getBalance());

        // Verify that the save method was called once
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testFindById() {
        Account account = new Account("testAddress", 100);

        when(accountRepository.findById("testAddress")).thenReturn(Optional.of(account));
        Optional<Account> foundAccount = accountRepository.findById("testAddress");

        assertEquals("testAddress", foundAccount.orElseThrow().getAddress());
        assertEquals(100, foundAccount.orElseThrow().getBalance());

        verify(accountRepository, times(1)).findById("testAddress");
    }

    @Test
    void testDeleteAccount() {
        Account account = new Account("testAddress", 100);

        doNothing().when(accountRepository).delete(account);
        accountRepository.delete(account);

        verify(accountRepository, times(1)).delete(account);
    }
}
