package edu.java.scrapper.domain;

import edu.java.scrapper.data.client.GithubClient;
import edu.java.scrapper.data.client.StackOverflowClient;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateSchedulerService {
    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;

    public LinkUpdateSchedulerService(
        GithubClient githubClient,
        StackOverflowClient stackOverflowClient
    ) {
        this.githubClient = githubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    /**
     * Schedule entrypoint for scrapping urls.
     */
    @Scheduled(fixedDelayString = "#{@interval}")
    public void update() {
        log.info(MessageFormat.format("Current time: {0}", System.currentTimeMillis()));
    }
}
