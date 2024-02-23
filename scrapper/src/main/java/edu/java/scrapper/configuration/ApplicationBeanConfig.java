package edu.java.scrapper.configuration;

import edu.java.scrapper.util.LoggerQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.logging.Logger;

@Configuration
public class ApplicationBeanConfig {
    @Bean
    @LoggerQualifier("rest-client-handler")
    public Logger provideRestClientExceptionLogger() {
        return Logger.getLogger("REST-CLIENT-HANDLER");
    }
}
