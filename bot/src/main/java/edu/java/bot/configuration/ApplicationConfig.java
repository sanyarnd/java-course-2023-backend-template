package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@Validated
@Setter
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@ConfigurationPropertiesScan
public class ApplicationConfig {
    @NotEmpty
    public String telegramToken;
}
