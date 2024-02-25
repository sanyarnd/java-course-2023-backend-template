package edu.java.scrapper.data.client.impl;

import edu.java.scrapper.data.client.StackOverflowClient;
import edu.java.scrapper.data.dto.stackoverflow.AnswersDTO;
import edu.java.scrapper.di.util.ApiQualifier;
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
    public AnswersDTO fetchAnswers(String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
            .retrieve().bodyToMono(AnswersDTO.class)
            .block();
    }
}
