package com.se300.ledger.controller;

import com.se300.ledger.model.Account;
import com.se300.ledger.model.Transaction;
import com.se300.ledger.service.LedgerAPI;
import com.se300.ledger.service.LedgerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "basicAuth")
public class LedgerRestController {

        @Autowired
        private LedgerAPI ledger;

        @Operation(summary = "Create Account By Address", tags = { "accounts" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
        @PostMapping("/accounts")
        public Account createAccount(@RequestBody Account account) throws LedgerException {
                return ledger.createAccount(account.getAddress());
        }

        @Operation(summary = "Retrieve Account By Id", tags = { "accounts" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "204", description = "There is no account with such address", content = {
                                        @Content(schema = @Schema()) }),
                        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
        @GetMapping("/accounts/{address}")
        public Account getAccount(@PathVariable String address) throws LedgerException {
                return ledger.getUncommittedBlock().getAccount(address);
        }

        // TODO: (attempt) Implement Transaction Retrieval REST Method
        @Operation(summary = "Retrieve Transaction By ID", tags = { "transactions" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = Transaction.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "Transaction not found", content = {
                                        @Content(schema = @Schema()) }),
                        @ApiResponse(responseCode = "500", content = {
                                        @Content(schema = @Schema()) })
        })
        @GetMapping("/transactions/{transactionId}")
        public Transaction getTransaction(@PathVariable String transactionId) {
                return ledger.getTransaction(transactionId);
        }

        // TODO: (done?) Implement Transaction Processing REST Method
        @Operation(summary = "Process Transaction", tags = { "transactions" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Transaction processed successfully", content = {
                                @Content(schema = @Schema(implementation = Transaction.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "400", description = "Invalid transaction data", content = {
                                        @Content(schema = @Schema()) }),
                        @ApiResponse(responseCode = "500", content = {
                                        @Content(schema = @Schema()) })
        })
        @PostMapping("/transactions")
        public String processTransaction(@RequestBody Transaction transaction) throws LedgerException {
                
                return ledger.processTransaction(transaction);

        }

}
