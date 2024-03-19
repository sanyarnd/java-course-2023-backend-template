package edu.java.scrapper.data.network.impl;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.core.request.LinkUpdateRequest;
import edu.java.core.response.github.GithubRepositoryResponse;
import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;
import edu.java.core.util.ApiQualifier;
import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.network.StackOverflowClient;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {
    private final Pattern pattern;
    private final WebClient webClient;
    private LinkContentRepository contentRepository;

    public StackOverflowClientImpl(@ApiQualifier("stack-overflow") String baseUrl) {
        this.pattern = Pattern.compile(baseUrl + "/questions/(\\w+)/answers?site=stackoverflow");
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

    @Autowired
    public void setContentRepository(LinkContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Boolean canHandle(Link link) {
        return pattern.matcher(link.getUrl()).matches();
    }

    @Override
    public Link handle(Link link) throws LinkIsUnreachable {
        Matcher matcher = pattern.matcher(link.getUrl());
        if (!matcher.matches()) {
            throw new LinkIsUnreachable();
        }
        String questionId = matcher.group(1);
        StackOverflowAnswersResponse response = fetchAnswers(questionId);
        contentRepository.findById(link.getId())
                .ifPresentOrElse(
                        linkContent -> contentRepository.update(linkContent.setRaw(response.toString()).setHash(response.hashCode())),
                        () -> contentRepository.add(new LinkContent(link.getId(), response.toString(), response.hashCode()))
                );
        return link.setLastUpdatedAt(OffsetDateTime.now());
    }
}
