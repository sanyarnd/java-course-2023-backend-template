package edu.java.scrapper.configuration;

import edu.java.scrapper.GitHubClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${api.github.base-url}")
    private String gitHubBaseUrl;

    @Bean
    public GitHubClient gitHubWebClient() {
        return new GitHubClient(gitHubBaseUrl);
    }
}
