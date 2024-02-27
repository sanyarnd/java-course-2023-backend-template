package edu.java.api.scrapper;

import edu.java.api.scrapper.dto.request.AddLinkRequest;
import edu.java.api.scrapper.dto.request.RemoveLinkRequest;
import edu.java.api.scrapper.dto.response.ApiErrorResponse;
import edu.java.api.scrapper.dto.response.LinkResponse;
import edu.java.api.scrapper.dto.response.ListLinksResponse;
import edu.java.api.scrapper.exception.ChatNotFoundException;
import edu.java.api.scrapper.exception.LinkAlreadyAddedException;
import edu.java.api.scrapper.exception.ScrapperClientException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {
    private static final String TG_CHAT_PATH = "/tg-chat/{id}";
    private static final String LINKS_PATH = "/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-chat-id";

    private final WebClient webClient;

    public ScrapperClient(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void addChat(Long id) {
        webClient.post().uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(Void.class)
            .block();
    }

    public void deleteChat(Long id) {
        webClient.delete().uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ChatNotFoundException(error.exceptionMessage()))))
            .bodyToMono(Void.class)
            .block();
    }

    public ListLinksResponse getLinks(Long id) {
        return webClient.get().uri(LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse addLink(Long id, String url) {
        return webClient.post().uri(LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(new AddLinkRequest(url)))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> {
                if (response.statusCode() == HttpStatus.CONFLICT) {
                    return response.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new LinkAlreadyAddedException(error.exceptionMessage())));
                }
                return response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage())));
            })
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse deleteLink(Long id, String url) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(url)))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
