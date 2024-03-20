package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.ApiErrorException;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.response.ApiErrorResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.NotificationConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@Slf4j
public class NotificationConnectorImpl implements NotificationConnector {
    private final WebClient webClient;

    public NotificationConnectorImpl(@ApiQualifier("bot") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public void update(LinkUpdateRequest request) {
        try {
            webClient.post()
                    .uri("/updates")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            clientResponse -> clientResponse
                                    .bodyToMono(ApiErrorResponse.class)
                                    .map(ApiErrorException::new)
                    )
                    .toBodilessEntity()
                    .block();
        } catch (ApiErrorException apiErrorException) {
            log.warn(String.valueOf(apiErrorException));
        }
    }
}
