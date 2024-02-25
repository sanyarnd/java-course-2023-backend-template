package edu.java.scrapper.client;

import edu.java.scrapper.response.QuestionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {
    @Value("${api.stackoverflow.base-url}")
    private String stackOverflowBaseUrl;
    private final WebClient webClient;

    public StackOverflowClient(String baseUrl) {
        String url = baseUrl;
        if (baseUrl.isEmpty()) {
            url = stackOverflowBaseUrl;
        }
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public QuestionResponse getQuestion(long id) {
        return webClient
            .get()
            .uri("/2.3/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }
}
