package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.github.Owner;
import edu.java.dto.github.Repository;
import edu.java.services.GithubService;
import java.time.OffsetDateTime;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GithubServiceTest {
    private WireMockServer wireMockServer;
    private GithubService githubService;

    @BeforeEach
    void init() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        githubService = new GithubService(WebClient.create("http://localhost:" + wireMockServer.port()));
    }

    @AfterEach
    void stop() {
        wireMockServer.stop();
    }

    @Test
    void getNewAnswersFromGithubTest() {
        String login = "octocat";
        String repository = "Hello-World";

        String responseBody = """
            {
              "id": 123,
              "name": "Hello-World",
              "owner": {
                "id": 456,
                "login": "octocat"
              },
              "updated_at": "2022-01-01T10:00:00Z",
              "pushed_at": "2022-01-02T15:30:00Z"
            }
            """;

        var uri = UriComponentsBuilder
            .fromPath("/repos/{user}/{repository}")
            .uriVariables(Map.of("user", login, "repository", repository));

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(uri.toUriString()))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)
            )
        );

        Mono<Repository> response = githubService.getNewAnswersFromGithub(login, repository);

        StepVerifier.create(response)
            .expectNextMatches(repo -> {
                Owner owner = repo.getOwner();
                return repo.getId() == 123 &&
                    repo.getName().equals("Hello-World") &&
                    owner.getId() == 456 &&
                    owner.getLogin().equals("octocat") &&
                    repo.getUpdatedAt().isEqual(OffsetDateTime.parse("2022-01-01T10:00:00Z")) &&
                    repo.getPushedAt().isEqual(OffsetDateTime.parse("2022-01-02T15:30:00Z"));
            })
            .verifyComplete();
    }
}
