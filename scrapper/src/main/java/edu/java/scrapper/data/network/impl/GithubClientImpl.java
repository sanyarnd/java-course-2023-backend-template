package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.network.GithubClient;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
@Slf4j
public class GithubClientImpl implements GithubClient {
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;
    private final String baseUrl;
    private final WebClient webClient;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public GithubRepositoryResponse fetchRepository(String username, String repository) {
        return webClient
                .get()
                .uri("/repos/{username}/{repository}", username, repository)
                .retrieve()
                .bodyToMono(GithubRepositoryResponse.class)
                .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofMillis(DURATION)))
                .block();
    }

    @Override
    public Pattern pathPattern() {
        return Pattern.compile(baseUrl + "/repos/(\\w+)/(\\w+)");
    }

    @Override
    public Boolean canHandle(String url) {
        return pathPattern().matcher(url).matches();
    }

    @Override
    public LinkUpdateRequest handle(String url) throws LinkIsUnreachable {
        Matcher matcher = pathPattern().matcher(url);
        if (!matcher.matches()) {
            throw new LinkIsUnreachable();
        }
        String username = matcher.group(1);
        String repository = matcher.group(2);
        GithubRepositoryResponse response = fetchRepository(username, repository);
        // TODO
        return null;
    }
}
