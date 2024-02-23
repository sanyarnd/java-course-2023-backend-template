package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.configuration.GitHubConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
@SpringBootTest
@WireMockTest(httpPort = 8089)
public class GitHubClientTest {

    private GitHubClient gitHubClient;

    @BeforeEach
    public void setUp() {
        GitHubConfig gitHubConfig = Mockito.mock(GitHubConfig.class);
        Mockito.when(gitHubConfig.getBaseUrl()).thenReturn("http://localhost:8089");
        gitHubClient = new GitHubClient(gitHubConfig);

        // Успешный ответ
        stubFor(get(urlEqualTo("/repos/user/success-repo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"name\": \"success-repo\", \"updated_at\": \"2020-01-01T00:00:00Z\"}")));

        // Ответ с ошибкой
        stubFor(get(urlEqualTo("/repos/user/error-repo"))
            .willReturn(aResponse()
                .withStatus(404)
                .withBody("{\"message\": \"Not Found\"}")));
    }

    @Test
    void fetchRepoInfoShouldReturnRepoDetailsOnSuccess() {
        StepVerifier.create(gitHubClient.fetchRepoInfo("user", "success-repo"))
            .expectNextMatches(repoInfo ->
                "success-repo".equals(repoInfo.name()) &&
                    OffsetDateTime.parse("2020-01-01T00:00:00Z").isEqual(repoInfo.updatedAt()))
            .verifyComplete();
    }

    @Test
    void fetchRepoInfoShouldHandleError() {
        StepVerifier.create(gitHubClient.fetchRepoInfo("user", "error-repo"))
            .expectErrorMatches(throwable -> throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
            .verify();
    }

}
