package edu.java.bot.data.impl;

import edu.java.bot.data.LinkTrackerRepository;
import edu.java.core.exception.LinkAlreadyTracked;
import edu.java.core.exception.LinkAlreadyNotTracked;
import edu.java.core.exception.UserIsNotAuthenticated;
import edu.java.core.response.LinkResponse;
import edu.java.core.response.ListLinksResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class LinkTrackerRepositoryImpl implements LinkTrackerRepository {
    private final WebClient webClient;

    public LinkTrackerRepositoryImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    private static Mono<? extends Throwable> apply(ClientResponse clientResponse) {
        return Mono.error(new UserIsNotAuthenticated());
    }

    @Override
    public List<String> getUserTrackedLinks(Long userId) throws UserIsNotAuthenticated {
        var response = webClient.get()
            .header("Tg-Chat-Id", String.valueOf(userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, LinkTrackerRepositoryImpl::apply)
            .bodyToMono(ListLinksResponse.class)
            .block();
        if (response != null) {
            return response.links().stream().map(LinkResponse::url).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public void setLinkTracked(Long userId, String link) throws UserIsNotAuthenticated, LinkAlreadyTracked {
        webClient.post()
            .header("Tg-Chat-Id", String.valueOf(userId))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> null)
            .toBodilessEntity()
            .block();
    }

    @Override
    public void setLinkUntracked(Long userId, String link)
        throws UserIsNotAuthenticated, LinkAlreadyNotTracked {

    }
}
