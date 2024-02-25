package edu.java.clients;

import edu.java.responses.GitHubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class GitHubClient {

    private final WebClient webClient;

    @Autowired
    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GitHubResponse> fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubResponse.class);
    }
}
