package edu.java.scrapper.data.network.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.core.exception.LinkCannotBeHandledException;
import edu.java.core.model.StackOverflowPersistenceData;
import edu.java.core.response.stackoverflow.Answer;
import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.Comment;
import edu.java.core.response.stackoverflow.CommentResponse;
import edu.java.core.util.JsonSerializer;
import edu.java.core.util.ReflectionComparator;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.StackOverflowConnector;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowClientImpl extends BaseClient implements JsonSerializer<StackOverflowPersistenceData> {
    private final static List<String> URLS = List.of(
            "https://api.stackexchange.com/questions/([^/]+)/comments?site=stackoverflow",
            "https://stackoverflow.com/questions/([^/]+)/([^/]+)"
    );
    private final LinkContentRepository contentRepository;
    private final StackOverflowConnector connector;
    private final ObjectMapper objectMapper;

    public StackOverflowClientImpl(
            LinkContentRepository contentRepository,
            StackOverflowConnector connector,
            ObjectMapper objectMapper) {
        super(URLS);
        this.contentRepository = contentRepository;
        this.connector = connector;
        this.objectMapper = objectMapper;
    }

    @Override
    public String handle(Link link) throws LinkCannotBeHandledException {
        // Parsing url into value tokens
        List<String> tokens = extractDataTokensFromLink(link.getUrl());
        // Getting responses from api
        AnswerResponse answers = connector.fetchAnswers(tokens.get(1));
        CommentResponse comments = connector.fetchComments(tokens.get(1));
        // Combining into current
        StackOverflowPersistenceData current = new StackOverflowPersistenceData(answers, comments);
        // Handle
        try {
            StackOverflowPersistenceData previous = contentRepository.findById(link.getId())
                    .map(LinkContent::getRaw)
                    .map(rawContent -> {
                        try {
                            return deserialize(rawContent);
                        } catch (JsonProcessingException e) {
                            return null;
                        }
                    })
                    .orElse(null);
            contentRepository.updateContent(new LinkContent(link.getId(), this.serialize(current), current.hashCode()));
            List<String> differences = ReflectionComparator.getDifference(previous, current);
            return (differences.size() == 0)
                    ? null
                    : differences.stream().map(s -> s + "\n").collect(Collectors.joining());
        } catch (JsonProcessingException | IllegalAccessException exception) {
            throw new LinkCannotBeHandledException(link.getUrl());
        }
    }

    @Override
    public String serialize(StackOverflowPersistenceData entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }

    @Override
    public StackOverflowPersistenceData deserialize(String from) throws JsonProcessingException {
        return objectMapper.readValue(from, StackOverflowPersistenceData.class);
    }
}
