package edu.java.client;

import edu.java.client.exception.BotClientException;
import edu.java.request.LinkUpdateRequest;
import edu.java.response.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotClient {
    private final WebClient webClient;

    public BotClient(@Qualifier("telegramBotClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public void update(LinkUpdateRequest linkUpdateRequest) {
        webClient.post().uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new BotClientException(error.exceptionMessage()))))
            .bodyToMono(Void.class)
            .block();
    }
}
