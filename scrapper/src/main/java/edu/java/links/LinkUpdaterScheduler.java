package edu.java.links;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    private void update() {
        log.info("Update links");
    }
}
