package com.se300.ledger.controller;

import com.se300.ledger.TestSmartStoreApplication;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(classes = TestSmartStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LedgerRestInternalMockControllerTest {

        @LocalServerPort
        private static Integer port;

        @BeforeAll
        static void init() {

                ClientAndServer.startClientAndServer(1090);

                new MockServerClient("localhost", 1090)
                                .when(
                                                request()
                                                                .withMethod("GET")
                                                                .withPath("/accounts"),
                                                Times.unlimited(),
                                                TimeToLive.unlimited(),
                                                0)
                                .respond(
                                                response()
                                                                .withBody("{\n  \"address\" : \"master\",\n \"balance\" : 2147483647\n}"));
                new MockServerClient("localhost", 1090)
                                .when(
                                                request()
                                                                .withMethod("GET")
                                                                .withPath("/transactions/1"),
                                                Times.unlimited(),
                                                TimeToLive.unlimited(),
                                                0)
                                .respond(
                                                response()
                                                                .withBody("{\n  \"transactionId\" : \"1\",\n \"amount\" : 38,\n \"fee\" : 10,\n \"note\" : \"testNote\",\n \"payer\" : {\n \"address\" : \"1\",\n \"balance\" : 100\n },\n \"receiver\" : {\n \"address\" : \"2\",\n \"balance\" : 200\n }\n}"));

        }

        @Test
        void testGetAccountById() throws JSONException {

                String expectedJson = "{\"address\" : \"master\", \"balance\" : 2147483647}";

                ExtractableResponse<Response> response = RestAssured
                                .given()
                                .filter(new RequestLoggingFilter())
                                .auth().basic("sergey", "chapman")
                                .contentType(ContentType.JSON)
                                .when()
                                .get("http://localhost:" + 1090 + "/accounts")
                                .then()
                                .statusCode(200)
                                .extract();

                JSONAssert.assertEquals(expectedJson, response.body().asPrettyString(), true);
        }

        @Test
        public void testGetTransactionById() throws JSONException {

                // TODO: (done) Implement Transaction Internal Retrieval Test Method
                String expectedJson = "{\"transactionId\":\"1\",\"amount\":38,\"fee\":10,\"note\":\"testNote\",\"payer\":{\"address\":\"1\",\"balance\":100},\"receiver\":{\"address\":\"2\",\"balance\":200}}";

                ExtractableResponse<Response> response = RestAssured
                                .given()
                                .filter(new RequestLoggingFilter())
                                .auth().basic("sergey", "chapman")
                                .contentType(ContentType.JSON)
                                .when()
                                .get("http://localhost:" + 1090 + "/transactions/1")
                                .then()
                                .statusCode(200)
                                .extract();

                JSONAssert.assertEquals(expectedJson, response.body().asPrettyString(), true);
        }
}