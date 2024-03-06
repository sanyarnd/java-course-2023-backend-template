package edu.java.stackoverflow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowClient implements StackoverflowClientInterface {
    private final WebClient webClient;

    public StackoverflowClient(@Qualifier("stackoverflowClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public StackoverflowResponse getStackoverflowResponse(String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve().bodyToMono(StackoverflowResponse.class).block();
    }
}
