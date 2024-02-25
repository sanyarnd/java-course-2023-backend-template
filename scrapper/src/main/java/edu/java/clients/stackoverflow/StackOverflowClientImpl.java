package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient stackOverflowWebClient;

    public StackOverflowClientImpl(WebClient stackOverflowWebClient) {
        this.stackOverflowWebClient = stackOverflowWebClient;
    }

    @Override
    public StackOverflowQuestionResponse getQuestions(Long stackOverflowQuestionId) {
        return stackOverflowWebClient.get()
            .uri("/2.3/questions/" + stackOverflowQuestionId)
            .retrieve()
            .bodyToMono(StackOverflowFullResponse.class)
            .map(StackOverflowFullResponse::questions)
            .map(List::getFirst)
            .block();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private record StackOverflowFullResponse(
        @JsonProperty("items") List<StackOverflowQuestionResponse> questions
    ) {

    }

}
