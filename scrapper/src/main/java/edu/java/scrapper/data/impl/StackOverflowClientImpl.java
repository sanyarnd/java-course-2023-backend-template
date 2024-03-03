package edu.java.scrapper.data.impl;

import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.StackOverflowClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClientImpl(@ApiQualifier("stack-overflow") String baseUrl) {
        this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public StackOverflowAnswersResponse fetchAnswers(String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
            .retrieve().bodyToMono(StackOverflowAnswersResponse.class)
            .block();
    }
}
