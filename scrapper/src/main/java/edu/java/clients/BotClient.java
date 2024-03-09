package edu.java.clients;

import edu.java.clients.dto.ApiErrorResponse;
import edu.java.clients.dto.LinkUpdate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {
    private final WebClient webClient;

    @Autowired
    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void postUpdates(Long id, String url, String description, List<Long> tgChatIds) {
        LinkUpdate linkUpdate = new LinkUpdate(id, url, description, tgChatIds);

        webClient.post()
            .uri("/updates")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(BotApiException::new)
            )
            .bodyToMono(Void.class).block();
    }
}
