package edu.java.scrapper.data;

import edu.java.scrapper.util.LoggerQualifier;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ClientErrorHandler implements RestClient.ResponseSpec.ErrorHandler {
    private final Logger logger;

    public ClientErrorHandler(@LoggerQualifier("rest-client-handler") Logger logger) {
        this.logger = logger;
    }

    @Override
    public void handle(@NotNull HttpRequest request, @NotNull ClientHttpResponse response) throws IOException {
        logger.log(Level.WARNING, response.getStatusText());
    }
}
