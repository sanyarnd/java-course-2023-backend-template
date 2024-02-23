package edu.java.scrapper.configuration;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final GitHubConfig gitHubConfig;
    private final StackOverflowConfig stackOverFlowConfig;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(gitHubConfig);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(stackOverFlowConfig);
    }
}
