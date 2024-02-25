package edu.java.scrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    @Value("${api.github.base-url}")
    private String gitNubBaseUrl;
    private final WebClient webClient;

    public GitHubClient(String baseUrl) {
        String url = baseUrl;
        if (baseUrl.isEmpty()) {
            url = gitNubBaseUrl;
        }
        this.webClient = WebClient.builder().baseUrl(url).build();
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
