package edu.java.link;

import java.util.logging.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class LinkUpdaterScheduler {

    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("Running link update task...");
    }
}
