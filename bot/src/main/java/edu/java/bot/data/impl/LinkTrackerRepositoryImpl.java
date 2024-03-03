package edu.java.bot.data.impl;

import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.LinkAlreadyTracked;
import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.LinkNotTracked;
import edu.java.core.exception.UnrecognizableException;
import edu.java.core.exception.UserNotAuthenticated;
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
import reactor.core.publisher.Mono;

@Component
public class LinkTrackerRepositoryImpl implements LinkTrackerRepository, ExceptionDeserializer {
    private final WebClient webClient;

    public LinkTrackerRepositoryImpl(@ApiQualifier("scrapper") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public List<String> getUserTrackedLinks(Long userId) throws UserNotAuthenticated {
        final var response = webClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new UserNotAuthenticated()))
            .bodyToMono(ListLinksResponse.class)
            .block();
        if (response != null) {
            return response.links().stream().map(LinkResponse::url).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public void setLinkTracked(Long userId, String link)
        throws UserNotAuthenticated, LinkAlreadyTracked, LinkIsUnreachable {
        webClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .body(BodyInserters.fromValue(new AddLinkRequest(link)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(this::deserialize)
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void setLinkUntracked(Long userId, String link)
        throws UserNotAuthenticated, LinkNotTracked {
        webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .bodyValue(new RemoveLinkRequest(link))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(this::deserialize)
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public Exception deserialize(ApiErrorResponse response) {
        final var exceptionName = response.exceptionName();
        if (exceptionName.equals(UserNotAuthenticated.class.getName())) {
            return new UserNotAuthenticated();
        } else if (exceptionName.equals(LinkAlreadyTracked.class.getName())) {
            return new LinkAlreadyTracked();
        } else if (exceptionName.equals(LinkIsUnreachable.class.getName())) {
            return new LinkIsUnreachable();
        } else if (exceptionName.equals(LinkNotTracked.class.getName())) {
            return new LinkNotTracked();
        } else {
            return new UnrecognizableException(response);
        }
    }
}
