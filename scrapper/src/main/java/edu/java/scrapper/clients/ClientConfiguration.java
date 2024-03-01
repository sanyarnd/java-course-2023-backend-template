package edu.java.scrapper.clients;

import edu.java.scrapper.clients.impl.GitHubClient;
import edu.java.scrapper.clients.impl.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean("github")
    public Client createGithubClient() {
        return new GitHubClient();
    }

    @Bean("stackoverflow")
    public Client createStackOverflowClient() {
        return new StackOverflowClient();
    }
}
