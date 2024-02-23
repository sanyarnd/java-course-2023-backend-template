package edu.java.scrapper.configuration;

import edu.java.scrapper.data.GithubClient;
import edu.java.scrapper.data.RestClientErrorHandler;
import edu.java.scrapper.util.ApiQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
    public GithubClient provideGithubClient(@ApiQualifier("github") String apiUrl, RestClientErrorHandler handler) {
        var client = RestClient.builder()
            .baseUrl(apiUrl)
            .defaultStatusHandler(HttpStatusCode::isError, handler)
            .build();
        var adapter = RestClientAdapter.create(client);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GithubClient.class);
    }
}
