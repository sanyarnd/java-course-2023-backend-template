package edu.java.clients.github;

import org.springframework.web.reactive.function.client.WebClient;

public class GithubClientImpl implements GithubClient {
    private final WebClient githubWebClient;

    public GithubClientImpl(WebClient githubWebClient) {
        this.githubWebClient = githubWebClient;
    }

    @Override
    public GithubRepositoryResponse getRepository(GithubRepository githubRepository) {
        return githubWebClient.get()
            .uri("/repos/" + githubRepository.owner() + "/" + githubRepository.repo())
            .retrieve()
            .bodyToMono(GithubRepositoryResponse.class)
            .block();
    }
}
