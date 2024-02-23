package edu.java.scrapper.client;

import edu.java.scrapper.configuration.StackOverflowConfig;
import edu.java.scrapper.dto.StackOverFlowQuestionsResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClient(StackOverflowConfig stackOverflowConfig) {
        this.webClient = WebClient.builder().baseUrl(stackOverflowConfig.getBaseUrl()).build();
    }

    public Mono<StackOverFlowQuestionsResponse> fetchQuestionInfo(int questionId) {
        return this.webClient.get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverFlowQuestionsResponse.class);
    }
}
