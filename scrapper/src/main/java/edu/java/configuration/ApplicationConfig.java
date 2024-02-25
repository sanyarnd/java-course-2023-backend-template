package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@EnableScheduling
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull BasicURLs basicURLs
    ) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record BasicURLs(String gitHubBasicURL, String stackOverflowBasicURL) {
    }
}
