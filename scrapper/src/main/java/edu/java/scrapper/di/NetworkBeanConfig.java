package edu.java.scrapper.di;

import edu.java.core.util.ApiQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetworkBeanConfig {
    @Bean
    @ApiQualifier("github")
    public String provideGithubEndpoint(ApplicationConfig config) {
        return config.api().github();
    }

    @Bean
    @ApiQualifier("stack-overflow")
    public String provideStackOverflowEndpoint(ApplicationConfig config) {
        return config.api().stackOverflow();
    }

    @Bean
    @ApiQualifier("bot")
    public String provideBotEndpoint(ApplicationConfig config) {
        return config.api().bot();
    }
}
