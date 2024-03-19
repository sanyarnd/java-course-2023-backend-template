package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.UnrecognizableException;
import edu.java.core.exception.util.ExceptionDeserializer;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.NotificationRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository, ExceptionDeserializer {
    private final WebClient webClient;

    public NotificationRepositoryImpl(@ApiQualifier("bot") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public void update(LinkUpdateRequest request) throws UnrecognizableException {
        webClient.post()
            .uri("/updates")
            .bodyValue(request)
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
        return new UnrecognizableException(response);
    }
}
