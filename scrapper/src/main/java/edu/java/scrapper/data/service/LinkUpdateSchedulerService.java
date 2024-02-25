package edu.java.scrapper.data.service;

import edu.java.scrapper.data.source.GithubSource;
import edu.java.scrapper.data.source.StackOverflowSource;
import edu.java.scrapper.util.LoggerQualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateSchedulerService {
    private final Logger logger;
    private final GithubSource githubSource;
    private final StackOverflowSource stackOverflowSource;

    public LinkUpdateSchedulerService(
        @LoggerQualifier("schedule-logger") Logger logger,
        GithubSource githubSource,
        StackOverflowSource stackOverflowSource
    ) {
        this.logger = logger;
        this.githubSource = githubSource;
        this.stackOverflowSource = stackOverflowSource;
    }

    /**
     * Schedule entrypoint for scrapping links.
     * Example usage for:
     * githubSource.fetchRepository("AlexCawl", "iot-connector-application")
     * stackOverflowSource.fetchAnswers("78046340", "stackoverflow")
     */
    @Scheduled(fixedDelayString = "#{@interval}")
    public void update() {
        logger.log(Level.WARNING, MessageFormat.format("Current time: {0}", System.currentTimeMillis()));
        logger.log(Level.INFO, githubSource.fetchRepository("AlexCawl", "iot-connector-application").toString());
        logger.log(Level.INFO, stackOverflowSource.fetchAnswers("78046340", "stackoverflow").toString());
    }
}
