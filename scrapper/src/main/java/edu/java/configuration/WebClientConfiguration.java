package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient stackOverflowClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("https://api.stackexchange.com/2.3");
        return WebClient.builder().uriBuilderFactory(factory).build();

    }

    @Bean
    public WebClient githubClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("https://api.github.com");
        return WebClient.builder().uriBuilderFactory(factory).build();

    }
}
