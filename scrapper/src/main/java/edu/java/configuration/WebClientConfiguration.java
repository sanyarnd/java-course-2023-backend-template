package edu.java.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public WebClient stackOverflowClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(applicationConfig.stackOverflowBaseUri());
        return WebClient.builder().uriBuilderFactory(factory).build();

    }

    @Bean
    public WebClient githubClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(applicationConfig.githubBaseUri());
        return WebClient.builder().uriBuilderFactory(factory).build();

    }
}
