package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.GithubClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
@Slf4j
public class GithubClientImpl implements GithubClient {
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;
    private final Pattern pattern;
    private final WebClient webClient;
    private LinkContentRepository contentRepository;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        this.pattern = Pattern.compile(baseUrl + "/repos/([^/]+)/([^/]+)");
        System.out.println(pattern.pattern());
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

    @Autowired
    public void setContentRepository(LinkContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Boolean canHandle(Link link) {
        System.out.println(link.getUrl() + " -> " + pattern.pattern());
        return pattern.matcher(link.getUrl()).matches();
    }

    @Override
    public Link handle(Link link) throws LinkIsUnreachable {
        Matcher matcher = pattern.matcher(link.getUrl());
        if (!matcher.matches()) {
            throw new LinkIsUnreachable();
        }
        String username = matcher.group(1);
        String repository = matcher.group(2);
        GithubRepositoryResponse response = fetchRepository(username, repository);
        var optional = contentRepository.findById(link.getId());
        System.out.println(optional);
        optional
                .ifPresentOrElse(
                        linkContent -> contentRepository.update(linkContent.setRaw(response.toString()).setHash(response.hashCode())),
                        () -> contentRepository.add(new LinkContent(link.getId(), response.toString(), response.hashCode()))
                );
        return link.setLastUpdatedAt(OffsetDateTime.now());
    }
}
