package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private ApplicationConfig applicationConfig;

    @Autowired
    public ClientConfiguration(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(WebClient.builder()
            .baseUrl(applicationConfig.basicURLs().scrapperBasicURL())
            .build());
    }
}
