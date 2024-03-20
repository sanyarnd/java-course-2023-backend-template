package edu.java.scrapper.data.network.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.response.github.GithubPersistenceData;
import edu.java.core.response.github.GithubRepositoryCommitResponse;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.core.response.github.GithubJsonSerializer;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.GithubConnector;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
@Slf4j
public class GithubClientImpl extends BaseClient implements GithubConnector, GithubJsonSerializer {
    private final static List<String> URLS = List.of(
            "https://api.github.com/repos/([^/]+)/([^/]+)",
            "https://github.com/([^/]+)/([^/]+)"
    );
    private final static int MAX_ATTEMPTS = 3;
    private final static int DURATION = 200;

    private final WebClient webClient;
    private LinkContentRepository contentRepository;
    private ObjectMapper mapper;

    public GithubClientImpl(@ApiQualifier("github") String baseUrl) {
        super(URLS);
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Autowired
    public void setContentRepository(LinkContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
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

    public List<GithubRepositoryCommitResponse> fetchRepositoryCommits(String username, String repository) {
        return webClient
                .get()
                .uri("/repos/{username}/{repository}/commits", username, repository)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GithubRepositoryCommitResponse>>() {})
                .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofMillis(DURATION)))
                .block();
    }

    @Override
    public Pair<Link, String> handle(Link link) throws LinkCannotBeHandledException {
        List<String> tokens = extractDataTokensFromLink(link.getUrl());
        GithubRepositoryResponse response = fetchRepository(tokens.get(1), tokens.get(2));
        String handleMessage = null;
        contentRepository.findById(link.getId())
                .ifPresentOrElse(
                        linkContent -> {
                            contentRepository.updateContent(
                                    linkContent.setRaw(response.toString()).setHash(response.hashCode())
                            );
                        },
                        () -> contentRepository.add(
                                new LinkContent(link.getId(), response.toString(), response.hashCode())
                        )
                );
        return Pair.of(link.setLastUpdatedAt(OffsetDateTime.now()), null);
    }

    private @Nullable String compareDataAndUpdate(GithubPersistenceData current, Long linkId) {
        Optional<GithubPersistenceData> previousOptional = contentRepository.findById(linkId)
                .map(LinkContent::getRaw)
                .map(rawObject -> {
                    try {
                        return this.deserialize(rawObject);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                });
        try {
            GithubPersistenceData previous = previousOptional.orElseThrow();
            // TODO
            return "";
        } catch (Exception exception) {
            contentRepository.add(new LinkContent(linkId, current.toString(), current.hashCode()));
            return null;
        }
    }

    @Override
    public GithubPersistenceData combine(GithubRepositoryResponse response, List<GithubRepositoryCommitResponse> commitResponse) {
        return new GithubPersistenceData()
                .setForksCount(response.forksCount())
                .setCommitsCount(commitResponse.size())
                .setSubscribersCount(response.subscribersCount())
                .setOpenIssuesCount(response.openIssuesCount())
                .setWatchersCount(response.watchersCount());
    }

    @Override
    public String serialize(GithubPersistenceData entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }

    @Override
    public GithubPersistenceData deserialize(String from) throws JsonProcessingException {
        return mapper.readValue(from, GithubPersistenceData.class);
    }
}
