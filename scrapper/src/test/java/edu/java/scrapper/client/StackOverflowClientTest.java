package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.configuration.StackOverflowConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
@SpringBootTest
@WireMockTest(httpPort = 8089)
public class StackOverflowClientTest {

    private StackOverflowClient stackOverflowClient;

    @BeforeEach
    public void setUp() {
        StackOverflowConfig stackOverflowConfig = Mockito.mock(StackOverflowConfig.class);
        Mockito.when(stackOverflowConfig.getBaseUrl()).thenReturn("http://localhost:8089");
        stackOverflowClient = new StackOverflowClient(stackOverflowConfig);

        // Имитация успешного ответа от API
        stubFor(get(urlPathEqualTo("/questions/123"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"items\": [{\"last_edit_date\": 123123123, \"question_id\": 123}]}")));

        // Имитация ответа с ошибкой
        stubFor(get(urlPathEqualTo("/questions/404"))
            .willReturn(aResponse()
                .withStatus(404)
                .withBody("{\"error_message\": \"Question not found.\"}")));
    }

    @Test
    void fetchQuestionInfoShouldReturnQuestionDetailsOnSuccess() {
        StepVerifier.create(stackOverflowClient.fetchQuestionInfo(123))
            .expectNextMatches(response ->
                response.questions().stream().anyMatch(question ->
                    question.id() == 123L &&
                        question.updatedAt().equals(OffsetDateTime.ofInstant(Instant.ofEpochSecond(123123123), ZoneOffset.UTC))))
            .verifyComplete();
    }

    @Test
    void fetchQuestionInfoShouldHandleError() {
        StepVerifier.create(stackOverflowClient.fetchQuestionInfo(404))
            .expectErrorMatches(throwable -> throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
            .verify();
    }
}
