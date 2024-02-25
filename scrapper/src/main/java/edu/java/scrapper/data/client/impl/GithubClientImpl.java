package edu.java.scrapper.data.client.impl;

import edu.java.scrapper.data.client.GithubClient;
import edu.java.scrapper.data.dto.github.RepositoryDTO;
import edu.java.scrapper.util.ApiQualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.Duration;

@Component
public class GithubClientImpl implements GithubClient {
    private final WebClient webClient;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public RepositoryDTO fetchRepository(String user, String repository) {
        return webClient
            .get()
            .uri("/repos/{username}/{repo}", user, repository)
            .retrieve()
            .bodyToMono(RepositoryDTO.class)
            .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
            .block();
    }
}
