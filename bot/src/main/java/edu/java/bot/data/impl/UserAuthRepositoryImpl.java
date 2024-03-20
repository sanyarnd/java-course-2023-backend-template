package edu.java.bot.data.impl;

import edu.java.bot.data.UserAuthRepository;
import edu.java.core.exception.ApiErrorException;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.util.ApiQualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserAuthRepositoryImpl implements UserAuthRepository {
    private final WebClient webClient;

    public UserAuthRepositoryImpl(@ApiQualifier("scrapper") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    private static final String ENDPOINT = "/tg-chat/{id}";

    @Override
    public void registerUser(Long userId) throws ApiErrorException {
        webClient.post()
            .uri(ENDPOINT, userId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorException::new)
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void deleteUser(Long userId) throws ApiErrorException {
        webClient.delete()
            .uri(ENDPOINT, userId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(ApiErrorException::new)
            )
            .toBodilessEntity()
            .block();
    }
}
