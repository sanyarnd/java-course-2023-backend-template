package edu.java.scrapper.configuration;

import edu.java.scrapper.util.LoggerQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
public class ApplicationBeanConfig {
    @Bean
    @LoggerQualifier("rest-client-handler")
    public Logger provideRestClientExceptionLogger() {
        return Logger.getLogger("REST-CLIENT-HANDLER");
    }

    @Bean
    @LoggerQualifier("schedule-logger")
    public Logger provideScheduleLogger() {
        return Logger.getLogger("SCHEDULE-LOGGER");
    }

    @Bean("interval")
    public long provideSchedulerInterval(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
