package edu.java.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubClientTest {
    private WireMockServer wireMockServer = new WireMockServer();

    @BeforeEach
    public void setUp() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetGitHubResponse() {
        String username = "testUsername";
        String repo = "testRepoName";
        String wireMockBaseUrl = "http://localhost:8080";

        stubFor(get(urlEqualTo("/repos/" + username + "/" + repo))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"id\": 12345, \"name\": \"testRepoName\", \"updated_at\": \"2024-02-18T11:03:29Z\", \"pushed_at\": \"2024-02-18T13:49:42Z\"}")));

        GitHubClient gitHubClient = new GitHubClient(WebClient.builder().baseUrl(wireMockBaseUrl).build());
        GitHubResponse gitHubResponse = gitHubClient.getGitHubResponse(username, repo);

        assertEquals(12345, gitHubResponse.id());
        assertEquals("testRepoName", gitHubResponse.name());
        assertEquals("2024-02-18T11:03:29Z", gitHubResponse.updatedAt());
        assertEquals("2024-02-18T13:49:42Z", gitHubResponse.pushedAt());
    }
}
