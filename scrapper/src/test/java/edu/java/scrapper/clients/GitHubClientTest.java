package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.clients.GitHubClient;
import edu.java.responses.GitHubResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class GitHubClientTest {

    private static WireMockServer wireMockServer;
    private static WebClient webClient;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        webClient = WebClient.builder()
            .baseUrl(wireMockServer.baseUrl())
            .build();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testFetchRepository() {
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/owner/repo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"name\": \"Test Repo\", \"description\": \"Test Description\", \"pushed_at\": \"2077-11-23T00:00:00Z\"}")
            ));

        GitHubClient gitHubClient = new GitHubClient(webClient);
        Mono<GitHubResponse> responseMono = gitHubClient.fetchRepository("owner", "repo");

        GitHubResponse response = responseMono.block();

        Assertions.assertEquals("Test Repo", response.getName());
        Assertions.assertEquals("Test Description", response.getDescription());
        Assertions.assertEquals("2077-11-23T00:00Z", response.getPushedAt().toString());
    }
}
