package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@WireMockTest(httpPort = 8081)
class StackOverflowClientImplTest {

    private StackOverflowClient client;

    @BeforeEach
    void setUp() {
        // Задаем WebClient с URL WireMock сервера
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();

        // Используем этот WebClient для создания клиента
        client = new StackOverflowClientImpl(webClient);
    }

    @Test
    void getQuestions() throws IOException {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/2.3/questions/78056287"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(Files.readString(Paths.get(
                    "src/test/resources/clients/stackoverflow_question_example_answer.json")))));

        var response = client.getQuestions(78056287L);

        Assertions.assertEquals(2024, response.getUpdatedAt().getYear());
        Assertions.assertEquals(Month.FEBRUARY, response.getUpdatedAt().getMonth());
        Assertions.assertEquals(25, response.getUpdatedAt().getDayOfMonth());
        Assertions.assertEquals(14, response.getUpdatedAt().getHour());
        Assertions.assertEquals(20, response.getUpdatedAt().getMinute());
        Assertions.assertEquals(53, response.getUpdatedAt().getSecond());
        Assertions.assertEquals(0, response.getUpdatedAt().getNano());
    }

    @Test
    void getQuestions_ClientError() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/2.3/questions/123"))
            .willReturn(aResponse()
                .withStatus(400)));

        Assertions.assertThrows(WebClientResponseException.class, () -> client.getQuestions(123L));
    }

    @Test
    void getQuestions_ServerError() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/2.3/questions/123"))
            .willReturn(aResponse()
                .withStatus(500)));

        Assertions.assertThrows(WebClientResponseException.class, () -> client.getQuestions(123L));
    }
}
