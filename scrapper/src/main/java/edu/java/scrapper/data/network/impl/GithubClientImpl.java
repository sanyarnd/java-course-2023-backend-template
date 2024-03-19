package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.GithubConnector;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
@Slf4j
public class GithubClientImpl extends BaseClient implements GithubConnector {
    private final static List<String> urls = List.of(
            "https://api.github.com/repos/([^/]+)/([^/]+)",
            "https://github.com/([^/]+)/([^/]+)"
    );
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;
    private final WebClient webClient;
    private LinkContentRepository contentRepository;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        super(urls);
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Autowired
    public void setContentRepository(LinkContentRepository contentRepository) {
        this.contentRepository = contentRepository;
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
    public Link handle(Link link) throws LinkIsUnreachable {
        List<String> tokens = extractDataTokensFromLink(link.getUrl());
        if (tokens.size() < 3) throw new LinkIsUnreachable();
        GithubRepositoryResponse response = fetchRepository(tokens.get(1), tokens.get(2));
        contentRepository.findById(link.getId())
                .ifPresentOrElse(
                        linkContent -> contentRepository.update(
                                linkContent.setRaw(response.toString()).setHash(response.hashCode())
                        ),
                        () -> contentRepository.add(
                                new LinkContent(link.getId(), response.toString(), response.hashCode())
                        )
                );
        return link.setLastUpdatedAt(OffsetDateTime.now());
    }
}
