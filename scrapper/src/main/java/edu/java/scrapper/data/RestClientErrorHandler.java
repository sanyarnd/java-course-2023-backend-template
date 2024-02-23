package edu.java.scrapper.data;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.IOException;

@Component
public class RestClientErrorHandler implements RestClient.ResponseSpec.ErrorHandler {
    @Override
    public void handle(@NotNull HttpRequest request, @NotNull ClientHttpResponse response) throws IOException {

    }
}
