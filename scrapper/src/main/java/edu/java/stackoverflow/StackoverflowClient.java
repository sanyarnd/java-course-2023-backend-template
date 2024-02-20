package edu.java.stackoverflow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowClient implements StackoverflowClientInterface {
    private final WebClient webClient;

    public StackoverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public StackoverflowResponse getStackoverflowResponse(@Qualifier("stackoverflowClient") String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve().bodyToMono(StackoverflowResponse.class).block();
    }
}
