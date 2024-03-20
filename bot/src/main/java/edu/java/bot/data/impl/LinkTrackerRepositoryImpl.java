package edu.java.bot.data.impl;

import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.ApiErrorException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.core.exception.util.ExceptionDeserializer;
import edu.java.core.request.AddLinkRequest;
import edu.java.core.request.RemoveLinkRequest;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.response.LinkResponse;
import edu.java.core.response.ListLinksResponse;
import edu.java.core.util.ApiQualifier;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LinkTrackerRepositoryImpl implements LinkTrackerRepository {
    private final WebClient webClient;

    public LinkTrackerRepositoryImpl(@ApiQualifier("scrapper") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    private static final String ENDPOINT = "/links";
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";

    @Override
    public List<String> getUserTrackedLinks(Long userId) throws ApiErrorException {
        final var response = webClient.get()
                .uri(ENDPOINT)
                .header(CHAT_ID_HEADER, String.valueOf(userId))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(this::extract)
                )
                .bodyToMono(ListLinksResponse.class)
                .block();
        if (response != null) {
            return response.links().stream().map(LinkResponse::url).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public void setLinkTracked(Long userId, String link) throws ApiErrorException {
        webClient.post()
                .uri(ENDPOINT)
                .header(CHAT_ID_HEADER, String.valueOf(userId))
                .body(BodyInserters.fromValue(new AddLinkRequest(link)))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorException::new)
                )
                .toBodilessEntity()
                .block();
    }

    @Override
    public void setLinkUntracked(Long userId, String link) throws ApiErrorException {
        webClient.method(HttpMethod.DELETE)
                .uri(ENDPOINT)
                .header(CHAT_ID_HEADER, String.valueOf(userId))
                .bodyValue(new RemoveLinkRequest(link))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(this::extract)
                )
                .toBodilessEntity()
                .block();
    }
}
