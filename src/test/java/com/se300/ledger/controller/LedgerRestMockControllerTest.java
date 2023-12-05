package com.se300.ledger.controller;

import com.se300.ledger.TestSmartStoreApplication;
import com.se300.ledger.model.Account;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestSmartStoreApplication.class)
@RunWith(SpringRunner.class)
public class LedgerRestMockControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAccountById() throws Exception {
        mockMvc.perform(get("/accounts/master")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.address").value("master"));
    }

    // @Test
    // public void testGetTransactionById() throws Exception {
    // String transactionId = "123";
    // mockMvc.perform(get("/transactions/" + transactionId))
    // .andExpect(status().isOk())
    // .andExpect(content().contentType("application/json"))
    // .andExpect(jsonPath("$.id").value(transactionId))
    // .andExpect(jsonPath("$.amount").value(50.0)) // Adjust these values based on
    // your actual transaction data
    // .andExpect(jsonPath("$.description").value("Test Transaction"));
    // }

    @Test
    public void testProcessAndGetTransactionById() throws Exception {

        mockMvc.perform(post("/transactions")
                .content(
                        "{\"transactionId\":\"0\",\"amount\":50,\"fee\":10,\"description\":\"Test Transaction\",\"payer\":{\"address\":\"master\",\"balance\":2147483647},\"payee\":{\"address\":\"master\",\"balance\":2147483647}}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.transactionId").value("0"));

        // TODO:(attempt) Implement Transaction Mock Retrieval Test Method
        mockMvc.perform(get("/transactions/0")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.transactionId").value("0"));
    }

}
