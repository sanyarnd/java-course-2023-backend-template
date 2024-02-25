package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.github.GithubClient;
import edu.java.clients.github.GithubClientImpl;
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
class GithubClientImplTest {

    private GithubClient client;

    @BeforeEach
    void setUp() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();
        client = new GithubClientImpl(webClient);
    }

    @Test
    void getRepository() throws IOException {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/user/repo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(Files.readString(Paths.get(
                    "src/test/resources/clients/github_repository_example_answer.json")))));

        var response = client.getRepository(new GithubClient.GithubRepository("user", "repo"));

        Assertions.assertEquals(2011, response.getUpdatedAt().getYear());
        Assertions.assertEquals(Month.JANUARY, response.getUpdatedAt().getMonth());
        Assertions.assertEquals(26, response.getUpdatedAt().getDayOfMonth());
        Assertions.assertEquals(19, response.getUpdatedAt().getHour());
        Assertions.assertEquals(14, response.getUpdatedAt().getMinute());
        Assertions.assertEquals(43, response.getUpdatedAt().getSecond());
        Assertions.assertEquals(0, response.getUpdatedAt().getNano());
    }

    @Test
    void getRepository_Error() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/user/repo"))
            .willReturn(aResponse()
                .withStatus(500)));

        Assertions.assertThrows(
            WebClientResponseException.class,
            () -> client.getRepository(new GithubClient.GithubRepository("user", "repo"))
        );
    }
}
