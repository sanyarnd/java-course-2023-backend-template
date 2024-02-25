package edu.java.scrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    @Value("${api.github.base-url}")
    private String gitNubBaseUrl;
    private final WebClient webClient;

    public GitHubClient(String baseUrl) {
        if (baseUrl.isEmpty()) {
            baseUrl = gitNubBaseUrl;
        }
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public RepositoryResponse getRepository(String user, String repository) {
        return webClient
            .get()
            .uri("/repos/{user}/{repository}", user, repository)
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }
}
