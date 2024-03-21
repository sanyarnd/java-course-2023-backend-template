package edu.java.scrapper.data.network.impl;

import edu.java.core.response.github.CommitResponse;
import edu.java.core.response.github.RepositoryResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.GithubConnector;
import java.time.Duration;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class GithubConnectorImpl implements GithubConnector {
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;
    private final WebClient webClient;

    public GithubConnectorImpl(@ApiQualifier("github") String baseUrl) {
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public RepositoryResponse fetchRepository(String username, String repository) {
        return webClient
                .get()
                .uri("/repos/{username}/{repository}", username, repository)
                .retrieve()
                .bodyToMono(RepositoryResponse.class)
                .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofMillis(DURATION)))
                .block();
    }

    @Override
    public List<CommitResponse> fetchRepositoryCommits(String username, String repository) {
        return webClient
                .get()
                .uri("/repos/{username}/{repository}/commits", username, repository)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommitResponse>>() {})
                .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofMillis(DURATION)))
                .block();
    }
}
