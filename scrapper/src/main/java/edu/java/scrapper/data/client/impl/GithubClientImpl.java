package edu.java.scrapper.data.client.impl;

import edu.java.scrapper.data.client.GithubClient;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.scrapper.di.util.ApiQualifier;
import java.time.Duration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class GithubClientImpl implements GithubClient {
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;
    private final WebClient webClient;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public GithubRepositoryResponse fetchRepository(String user, String repository) {
        return webClient
            .get()
            .uri("/repos/{username}/{repo}", user, repository)
            .retrieve()
            .bodyToMono(GithubRepositoryResponse.class)
            .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofMillis(DURATION)))
            .block();
    }
}
