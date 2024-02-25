package edu.java.configuration;

import edu.java.clients.github.GithubClient;
import edu.java.clients.github.GithubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Value("${github.api.baseUrl:https://api.github.com}")
    private String githubBaseUrl;

    @Value("${stackoverflow.api.baseUrl:https://api.stackexchange.com}")
    private String stackoverflowBaseUrl;

    @Bean
    public GithubClient githubWebClient() {
        return new GithubClientImpl(
            WebClient.builder()
                .baseUrl(githubBaseUrl)
                .build()
        );
    }

    @Bean
    public StackOverflowClient stackoverflowWebClient() {
        return new StackOverflowClientImpl(
            WebClient.builder()
                .baseUrl(stackoverflowBaseUrl)
                .build()
        );
    }
}
