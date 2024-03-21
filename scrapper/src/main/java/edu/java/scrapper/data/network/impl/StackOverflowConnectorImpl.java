package edu.java.scrapper.data.network.impl;

import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.CommentResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.StackOverflowConnector;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
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
    public List<AnswerResponse> fetchAnswers(String questionId) {
        return webClient
                .get()
                .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AnswerResponse>>() {})
                .block();
    }

    @Override
    public List<CommentResponse> fetchComments(String questionId) {
        return webClient
                .get()
                .uri("/questions/{questionId}/comments?order=desc&sort=creation&site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommentResponse>>() {})
                .block();
    }
}
