package edu.java.link;

import java.time.Duration;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class LinkUpdaterScheduler {

    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Value("${app.scheduler.interval}")
    private Duration interval;

    @Scheduled(fixedDelayString = "#{@interval.toMillis()}")
    public void update() {
        LOGGER.info("Running link update task...");
    }
}
