package edu.java.scrapper.di;

import edu.java.scrapper.data.db.repository.BindingRepository;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import edu.java.scrapper.data.db.repository.impl.jdbc.JdbcBindingRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jdbc.JdbcLinkContentRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jdbc.JdbcLinkRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jdbc.JdbcTelegramChatRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    public LinkRepository createLinkRepository(JdbcClient client) {
        return new JdbcLinkRepositoryImpl(client);
    }

    @Bean
    public LinkContentRepository createLinkContentRepository(JdbcClient client) {
        return new JdbcLinkContentRepositoryImpl(client);
    }

    @Bean
    public TelegramChatRepository createTelegramChatRepository(JdbcClient client) {
        return new JdbcTelegramChatRepositoryImpl(client);
    }

    @Bean
    public BindingRepository createBindingRepository(JdbcClient client) {
        return new JdbcBindingRepositoryImpl(client);
    }
}
