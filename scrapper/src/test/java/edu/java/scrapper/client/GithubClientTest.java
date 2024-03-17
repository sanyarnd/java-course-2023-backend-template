package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.data.network.GithubClient;
import edu.java.scrapper.data.network.impl.GithubClientImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@WireMockTest
public class GithubClientTest {
    private static WireMockServer server;
    private static GithubClient client;

    @BeforeAll
    public static void init() {
        server = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        server.start();
        client = new GithubClientImpl(server.baseUrl());
        WireMock.configureFor(server.port());
    }

    @AfterAll
    public static void clean() {
        server.stop();
    }

    @Test
    public void fetchRepositoryTest() {
        var username = "AlexCawl";
        var repository = "iot-connector-application";
        var body = """
                {
                  "pushed_at": "2024-02-10T10:05:49Z",
                  "subscribers_count": 1,
                  "id": 721347253,
                  "forks": 0,
                  "visibility": "public",
                  "network_count": 0,
                  "full_name": "AlexCawl/iot-connector-application",
                  "size": 605,
                  "name": "iot-connector-application",
                  "open_issues_count": 0,
                  "description": null,
                  "created_at": "2023-11-20T21:48:47Z",
                  "updated_at": "2024-01-24T20:34:19Z",
                  "owner": {
                    "login": "AlexCawl",
                    "type": "User",
                    "id": 91064467
                  },
                  "open_issues": 0,
                  "watchers_count": 0,
                  "forks_count": 0
                }
            """;
        stubFor(
            get(urlMatching(String.format("/repos/%s/%s", username, repository)))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", "application/json")
                    .withBody(body))
        );

        var response = client.fetchRepository(username, repository);
        Assertions.assertEquals(721347253, response.id());
        Assertions.assertEquals("iot-connector-application", response.name());
        Assertions.assertEquals("2023-11-20T21:48:47Z", response.createdAt().toString());
        Assertions.assertEquals("2024-01-24T20:34:19Z", response.updatedAt().toString());
    }
}
