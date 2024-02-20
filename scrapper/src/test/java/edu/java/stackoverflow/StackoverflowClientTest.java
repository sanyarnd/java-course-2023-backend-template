package edu.java.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackoverflowClientTest {

    WireMockServer wireMockServer = new WireMockServer();

    @BeforeEach
    public void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetStackoverflowResponse() {
        long questionId = 12345678;
        String wireMockBaseUrl = "http://localhost:8080";

        stubFor(get(urlEqualTo("/questions/" + questionId + "?site=stackoverflow"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"items\": [{ \"last_activity_date\": \"2024-02-20T12:00:00Z\" }]}")));

        StackoverflowClient stackoverflowClient =
            new StackoverflowClient(WebClient.builder().baseUrl(wireMockBaseUrl).build());
        StackoverflowResponse stackoverflowResponse = stackoverflowClient.getStackoverflowResponse("12345678910");
        StackoverflowQuestion stackoverflowQuestion = stackoverflowResponse.items().getFirst();

        assertEquals("2024-02-20T12:00:00Z", stackoverflowQuestion.lastActivityDate());
    }
}
