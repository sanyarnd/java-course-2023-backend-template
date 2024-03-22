package edu.java.scrapper.data.network.impl;

import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.CommentResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.StackOverflowConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowConnectorImpl implements StackOverflowConnector {
    private final WebClient webClient;

    public StackOverflowConnectorImpl(@ApiQualifier("stack-overflow") String baseUrl) {
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public AnswerResponse fetchAnswers(String questionId) {
        return webClient
                .get()
                .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(AnswerResponse.class)
                .block();
    }

    @Override
    public CommentResponse fetchComments(String questionId) {
        return webClient
                .get()
                .uri("/questions/{questionId}/comments?order=desc&sort=creation&site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(CommentResponse.class)
                .block();
    }
}
