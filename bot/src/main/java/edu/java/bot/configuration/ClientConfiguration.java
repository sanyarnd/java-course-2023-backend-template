package edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@Configuration
public class ClientConfiguration {
    @NotNull
    @Value("${clients.base-url.scrapper:http://localhost:8080}")
    String baseUrlScrapper;

    @Bean
    public WebClient getScrapperClient() {
        return WebClient.builder().baseUrl(baseUrlScrapper).build();
    }
}
