package edu.java.bot.data.impl;

import edu.java.bot.data.UserAuthRepository;
import edu.java.core.exception.UnrecognizableException;
import edu.java.core.exception.UserAlreadyRegistered;
import edu.java.core.exception.UserNotRegistered;
import edu.java.core.exception.util.ExceptionDeserializer;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.util.ApiQualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserAuthRepositoryImpl implements UserAuthRepository, ExceptionDeserializer {
    private final WebClient webClient;

    public UserAuthRepositoryImpl(@ApiQualifier("scrapper") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    private static final String ENDPOINT = "/tg-chat/{id}";

    @Override
    public void registerUser(Long userId) throws UserAlreadyRegistered {
        webClient.post()
            .uri(ENDPOINT, userId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class).map(this::deserialize)
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void deleteUser(Long userId) throws UserNotRegistered {
        webClient.delete()
            .uri(ENDPOINT, userId)
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
        if (exceptionName.equals(UserAlreadyRegistered.class.getName())) {
            return new UserAlreadyRegistered();
        } else if (exceptionName.equals(UserNotRegistered.class.getName())) {
            return new UserNotRegistered();
        } else {
            return new UnrecognizableException(response);
        }
    }
}
