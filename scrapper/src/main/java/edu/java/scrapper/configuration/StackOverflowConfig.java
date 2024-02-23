package edu.java.scrapper.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.stackoverflow")
public class StackOverflowConfig {
    private String baseUrl = "https://api.stackexchange.com/2.2";
}
