package edu.java.scrapper.configuration;

import edu.java.scrapper.util.LoggerQualifier;
import java.time.Duration;
import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationBeanConfig {
    @Bean
    @LoggerQualifier("schedule-logger")
    public Logger provideScheduleLogger() {
        return Logger.getLogger("SCHEDULE-LOGGER");
    }

    @Bean("interval")
    public Duration provideSchedulerInterval(ApplicationConfig config) {
        return config.scheduler().interval();
    }
}
