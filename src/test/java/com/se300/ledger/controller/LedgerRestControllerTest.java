package com.se300.ledger.controller;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import com.se300.ledger.repository.AccountRepository;
import com.se300.ledger.repository.TransactionRepository;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(classes = TestSmartStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LedgerRestControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;

    @BeforeAll
    static void init() {

        headers = new HttpHeaders();
        headers.setBasicAuth("sergey", "chapman");
    }

    @Test
    public void testGetAccountById() throws IllegalStateException, JSONException {

        String expectedJson = "{\"address\" : \"master\", \"balance\" : 2147483647}";

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/accounts/master", HttpMethod.GET, new HttpEntity<String>(headers),
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
    }

    @Test
    public void testPostAccount() throws IllegalStateException, JSONException {

        String expectedJson = "{\"address\" : \"sergey\", \"balance\" : 0}";
        Account account = new Account("sergey", 0);

        HttpEntity<Account> request = new HttpEntity<>(account, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/accounts",
                request, String.class);

        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
    }

    // TODO: (done?) Implement Transaction Processing Test Method
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testPostTransaction() throws IllegalStateException, JSONException {
        // Prepare a JSON string matching the Transaction object structure

        String expectedJson = "{\"transactionId\":\"tx123\",\"amount\":90,\"fee\":10,\"note\":\"Test transaction\",\"payer\":{\"address\":\"address1\",\"balance\":900},\"receiver\":{\"address\":\"address2\",\"balance\":1090}}";
        Account account1 = new Account("address1", 1000);
        Account account2 = new Account("address2", 1000);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Transaction transaction = new Transaction("tx123", 90, 10, "Test transaction", account1, account2);
        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/transactions",
                request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
    }

    // TODO: (done?) Implement Transaction Retrieval Test Method
    @Test
    public void testGetTransactionById() throws IllegalStateException, JSONException {

        String expectedJson = "{\"transactionId\":\"100\",\"amount\":90,\"fee\":10,\"note\":\"Test transaction\",\"payer\":{\"address\":\"address1\",\"balance\":900},\"receiver\":{\"address\":\"address2\",\"balance\":1090}}";
        Account account1 = new Account("address1", 1000);
        Account account2 = new Account("address2", 1000);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Transaction transaction = new Transaction("100", 90, 10, "Test transaction", account1, account2);
        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);

        ResponseEntity<String> postString = restTemplate.postForEntity("http://localhost:" + port + "/transactions",
                request, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/transactions/100", HttpMethod.GET, new HttpEntity<String>(headers),
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
    }

}
