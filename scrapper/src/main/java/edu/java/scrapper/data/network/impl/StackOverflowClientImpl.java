package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.BaseClient;
import edu.java.scrapper.data.network.StackOverflowConnector;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl extends BaseClient implements StackOverflowConnector {
    private final static List<String> urls = List.of(
            "https://api.stackexchange.com/questions/([^/]+)/answers?site=stackoverflow",
            "https://stackoverflow.com/questions/([^/]+)/([^/]+)"
    );
    private final WebClient webClient;
    private LinkContentRepository contentRepository;

    public StackOverflowClientImpl(@ApiQualifier("stack-overflow") String baseUrl) {
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
    public StackOverflowAnswersResponse fetchAnswers(String questionId) {
        return webClient
                .get()
                .uri("/questions/{questionId}/answers?site=stackoverflow", questionId)
                .retrieve().bodyToMono(StackOverflowAnswersResponse.class)
                .block();
    }

    @Override
    public Link handle(Link link) throws LinkIsUnreachable {
        List<String> tokens = extractDataTokensFromLink(link);
        if (tokens.size() < 2) throw new LinkIsUnreachable();
        StackOverflowAnswersResponse response = fetchAnswers(tokens.get(1));
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
