package edu.java.scrapper.data.network.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.model.GithubPersistenceData;
import edu.java.core.response.github.CommitResponse;
import edu.java.core.response.github.RepositoryResponse;
import edu.java.core.util.DifferenceComparator;
import edu.java.core.util.JsonSerializer;
import edu.java.core.util.ReflectionComparator;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.GithubConnector;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GithubClientImpl
        extends BaseClient
        implements JsonSerializer<GithubPersistenceData>, DifferenceComparator<GithubPersistenceData> {
    private final static List<String> URLS = List.of(
            "https://api.github.com/repos/([^/]+)/([^/]+)",
            "https://github.com/([^/]+)/([^/]+)"
    );
    private final GithubConnector connector;
    private final LinkContentRepository contentRepository;
    private final ObjectMapper objectMapper;

    public GithubClientImpl(GithubConnector connector, LinkContentRepository contentRepository, ObjectMapper objectMapper) {
        super(URLS);
        this.connector = connector;
        this.contentRepository = contentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public String handle(Link link) throws LinkCannotBeHandledException {
        // Parsing url into value tokens
        List<String> tokens = extractDataTokensFromLink(link.getUrl());
        // Getting responses from api
        RepositoryResponse repository = connector.fetchRepository(tokens.get(1), tokens.get(2));
        List<CommitResponse> commits = connector.fetchRepositoryCommits(tokens.get(1), tokens.get(2));
        // Combining into current
        GithubPersistenceData current = new GithubPersistenceData(repository, commits);
        // Handle
        try {
            // Get previous data
            GithubPersistenceData previous = contentRepository.get(link.getId())
                    .map(LinkContent::getRaw)
                    .map(rawContent -> {
                        try {
                            return deserialize(rawContent);
                        } catch (JsonProcessingException e) {
                            return null;
                        }
                    })
                    .orElse(null);
            // Update with current data
            contentRepository.upsert(new LinkContent(link.getId(), this.serialize(current), current.hashCode()));
            // Get difference
            List<String> differences = getDifference(previous, current);
            return (differences.size() == 0)
                    ? null
                    : differences.stream().map(s -> s + "\n").collect(Collectors.joining());
        } catch (JsonProcessingException | IllegalStateException exception) {
            throw new LinkCannotBeHandledException(link.getUrl());
        }
    }

    @Override
    public String serialize(GithubPersistenceData entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }

    @Override
    public GithubPersistenceData deserialize(String from) throws JsonProcessingException {
        return objectMapper.readValue(from, GithubPersistenceData.class);
    }

    @Override
    public List<String> getDifference(GithubPersistenceData before, GithubPersistenceData next) throws IllegalStateException {
        try {
            return ReflectionComparator.getDifference(before, next);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
