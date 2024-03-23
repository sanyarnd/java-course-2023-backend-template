package edu.java.scrapper.di;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationBeanConfig {
    @Bean("interval")
    public Duration provideSchedulerInterval(ApplicationConfig config) {
        return config.scheduler().interval();
    }

    @Bean("expiration")
    public Duration provideLinkExpiration(ApplicationConfig config) {
        return config.scheduler().linkExpiration();
    }
}
