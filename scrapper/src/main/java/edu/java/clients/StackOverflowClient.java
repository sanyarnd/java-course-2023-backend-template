package edu.java.clients;

import edu.java.responses.StackOverflowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowClient {
    private final WebClient webClient;

    @Autowired
    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<StackOverflowResponse> fetchQuestion(int questionId) {
        return webClient.get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class);
    }
}

