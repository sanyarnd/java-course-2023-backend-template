package edu.java.scrapper.data.service;

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

    public LinkUpdateSchedulerService(@LoggerQualifier("schedule-logger") Logger logger) {
        this.logger = logger;
    }


    @Scheduled(fixedDelayString = "#{@interval}")
    public void schedule() {
        logger.log(Level.WARNING, MessageFormat.format("Current time: {0}", System.currentTimeMillis()));
    }
}
