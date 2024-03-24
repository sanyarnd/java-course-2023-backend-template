package edu.java.scrapper.di;

import edu.java.scrapper.data.db.repository.BindingRepository;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import edu.java.scrapper.data.db.repository.impl.jpa.JpaBindingRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jpa.JpaLinkContentRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jpa.JpaLinkRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jpa.JpaTelegramChatRepositoryImpl;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.BindingDao;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.LinkContentDao;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.LinkDao;
import edu.java.scrapper.data.db.repository.impl.jpa.dao.TelegramChatDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkRepository createLinkRepository(LinkDao linkDao) {
        return new JpaLinkRepositoryImpl(linkDao);
    }

    @Bean
    public LinkContentRepository createLinkContentRepository(LinkContentDao linkContentDao) {
        return new JpaLinkContentRepositoryImpl(linkContentDao);
    }

    @Bean
    public TelegramChatRepository createTelegramChatRepository(TelegramChatDao telegramChatDao) {
        return new JpaTelegramChatRepositoryImpl(telegramChatDao);
    }

    @Bean
    public BindingRepository createBindingRepository(
            BindingDao bindingDao,
            LinkDao linkDao,
            TelegramChatDao telegramChatDao
    ) {
        return new JpaBindingRepositoryImpl(bindingDao, linkDao, telegramChatDao);
    }
}
