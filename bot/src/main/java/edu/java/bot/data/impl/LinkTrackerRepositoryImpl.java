package edu.java.bot.data.impl;

import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.LinkAlreadyNotTracked;
import edu.java.core.exception.LinkAlreadyTracked;
import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.exception.UserIsNotAuthenticated;
import edu.java.core.request.AddLinkRequest;
import edu.java.core.request.RemoveLinkRequest;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.response.LinkResponse;
import edu.java.core.response.ListLinksResponse;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LinkTrackerRepositoryImpl implements LinkTrackerRepository {
    private final WebClient webClient;

    public LinkTrackerRepositoryImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    private static RuntimeException extractException(ApiErrorResponse apiErrorResponse) {
        final var exceptionName = apiErrorResponse.exceptionName();
        if (exceptionName.equals(UserIsNotAuthenticated.class.getName())) {
            return new UserIsNotAuthenticated();
        } else if (exceptionName.equals(LinkAlreadyTracked.class.getName())) {
            return new LinkAlreadyTracked();
        } else if (exceptionName.equals(LinkIsUnreachable.class.getName())) {
            return new LinkIsUnreachable();
        } else if (exceptionName.equals(LinkAlreadyNotTracked.class.getName())) {
            return new LinkAlreadyNotTracked();
        } else {
            return new IllegalStateException();
        }
    }

    @Override
    public List<String> getUserTrackedLinks(Long userId) throws UserIsNotAuthenticated {
        final var response = webClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new UserIsNotAuthenticated()))
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
        throws UserIsNotAuthenticated, LinkAlreadyTracked, LinkIsUnreachable {
        webClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .body(BodyInserters.fromValue(new AddLinkRequest(link)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .map(LinkTrackerRepositoryImpl::extractException)
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void setLinkUntracked(Long userId, String link)
        throws UserIsNotAuthenticated, LinkAlreadyNotTracked {
        webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(userId))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(link)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .map(LinkTrackerRepositoryImpl::extractException)
            )
            .toBodilessEntity()
            .block();
    }
}
