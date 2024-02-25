package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
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
    public GitHubClient gitHubClient() {
        return new GitHubClient(WebClient.builder()
            .baseUrl(applicationConfig.basicURLs().gitHubBasicURL())
            .build());
    }

    @Bean
    public StackOverflowClient stackOverFlowClient() {
        return new StackOverflowClient(WebClient.builder()
            .baseUrl(applicationConfig.basicURLs().stackOverflowBasicURL())
            .build());
    }
}
