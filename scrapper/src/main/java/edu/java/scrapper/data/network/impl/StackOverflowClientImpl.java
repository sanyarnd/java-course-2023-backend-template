package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.StackOverflowClient;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {
    private final String baseUrl;
    private final WebClient webClient;

    public StackOverflowClientImpl(@ApiQualifier("stack-overflow") String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public StackOverflowAnswersResponse fetchAnswers(String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
            .retrieve().bodyToMono(StackOverflowAnswersResponse.class)
            .block();
    }

    @Override
    public Pattern pathPattern() {
        return Pattern.compile(baseUrl + "/questions/(\\w+)/answers?site=stackoverflow");
    }

    @Override
    public Boolean canHandle(String url) {
        return pathPattern().matcher(url).matches();
    }

    @Override
    public LinkUpdateRequest handle(String url) throws LinkIsUnreachable {
        // TODO
        return null;
    }
}
