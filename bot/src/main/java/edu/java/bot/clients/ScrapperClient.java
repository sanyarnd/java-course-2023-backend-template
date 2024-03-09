package edu.java.bot.clients;

import edu.java.bot.clients.dto.AddLinkRequest;
import edu.java.bot.clients.dto.ApiErrorResponse;
import edu.java.bot.clients.dto.LinkResponse;
import edu.java.bot.clients.dto.ListLinkResponse;
import edu.java.bot.clients.dto.RemoveLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperClient {
    private final WebClient webClient;
    private static final String TG_CHAT_PATH = "/tg-chat/{id}";
    private static final String LINKS_PATH = "/links";
    private static final String LINKS_HEADER_TG_CHAT_ID = "Tg-Chat-Id";

    @Autowired
    public ScrapperClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void postTgChat(Long id) {

        webClient.post()
            .uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ScrapperApiException::new)
            )
            .bodyToMono(Void.class).block();
    }

    public void deleteTgChat(Long id) {

        webClient.delete()
            .uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(
                status -> HttpStatus.BAD_REQUEST.equals(status) || HttpStatus.NOT_FOUND.equals(status),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ScrapperApiException::new)
            )
            .bodyToMono(Void.class).block();
    }

    public ListLinkResponse getLinks(Long chatId) {
        return webClient.get()
            .uri(LINKS_PATH)
            .header(LINKS_HEADER_TG_CHAT_ID, chatId.toString())
            .retrieve()
            .onStatus(
                status -> HttpStatus.BAD_REQUEST.equals(status) || HttpStatus.NOT_FOUND.equals(status),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ScrapperApiException::new)
            )
            .bodyToMono(ListLinkResponse.class).block();
    }

    public LinkResponse postLink(Long chatId, String url) {
        return webClient.post()
            .uri(LINKS_PATH)
            .header(LINKS_HEADER_TG_CHAT_ID, chatId.toString())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(BodyInserters.fromValue(new AddLinkRequest(url)))
            .retrieve()
            .onStatus(
                status -> HttpStatus.BAD_REQUEST.equals(status) || HttpStatus.NOT_FOUND.equals(status),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ScrapperApiException::new)
            )
            .bodyToMono(LinkResponse.class).block();
    }

    public LinkResponse deleteLink(Long chatId, String url) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_PATH)
            .header(LINKS_HEADER_TG_CHAT_ID, chatId.toString())
            .body(BodyInserters.fromValue(new RemoveLinkRequest(url)))
            .retrieve()
            .onStatus(
                status -> HttpStatus.BAD_REQUEST.equals(status) || HttpStatus.NOT_FOUND.equals(status),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ScrapperApiException::new)
            )
            .bodyToMono(LinkResponse.class).block();
    }
}
