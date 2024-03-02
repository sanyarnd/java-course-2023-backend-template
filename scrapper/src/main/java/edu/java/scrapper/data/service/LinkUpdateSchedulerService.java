package edu.java.scrapper.data.service;

import edu.java.scrapper.data.client.GithubClient;
import edu.java.scrapper.data.client.StackOverflowClient;
import edu.java.scrapper.util.LoggerQualifier;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateSchedulerService {
    private final Logger logger;
    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;

    public LinkUpdateSchedulerService(
        @LoggerQualifier("schedule-logger") Logger logger,
        GithubClient githubClient,
        StackOverflowClient stackOverflowClient
    ) {
        this.logger = logger;
        this.githubClient = githubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    /**
     * Schedule entrypoint for scrapping urls.
     */
    @Scheduled(fixedDelayString = "#{@interval}")
    public void update() {
        logger.log(Level.WARNING, MessageFormat.format("Current time: {0}", System.currentTimeMillis()));
    }
}
