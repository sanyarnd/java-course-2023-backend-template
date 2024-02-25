package edu.java.services;

import edu.java.dto.stackoverflow.Answer;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StackOverflowService {

    private final WebClient stackOverflowClient;

    @Autowired
    public StackOverflowService(WebClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    public Flux<Answer> getNewAnswersFromStackOverflow(long questionId, OffsetDateTime from) {
        return stackOverflowClient
            .get()
            .uri(
                "questions/{questionId}/answers?order=desc&min={from}&sort=activity&site=stackoverflow",
                questionId,
                from.toEpochSecond()
            )
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                .flatMap(error -> Mono.error(new RuntimeException("StackOverflow RuntimeException"))))
            .bodyToFlux(Answer.class);
    }

}
