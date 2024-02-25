package edu.java.services;

import edu.java.dto.github.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GithubService {

    private final WebClient githubClient;

    @Autowired
    public GithubService(WebClient githubClient) {
        this.githubClient = githubClient;
    }

    public Mono<Repository> getNewAnswersFromGithub(String login, String repository) {
        return githubClient
            .get()
            .uri("/repos/{user}/{repository}", login, repository)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                .flatMap(error -> Mono.error(new RuntimeException("Github RuntimeException"))))
            .bodyToMono(Repository.class);
    }
}
