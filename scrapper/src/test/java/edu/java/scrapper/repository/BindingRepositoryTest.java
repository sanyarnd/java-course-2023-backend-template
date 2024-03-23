package edu.java.scrapper.repository;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BindingRepository;
import edu.java.scrapper.data.db.repository.LinkRepository;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class BindingRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private BindingRepository repository;

    @Autowired
    private LinkRepository linkRepository;
    private List<Link> bindedLinks;

    @Autowired
    private TelegramChatRepository telegramChatRepository;
    private List<TelegramChat> bindedChats;

    @BeforeEach
    @Transactional
    @Rollback
    public void initLinks() {
        bindedLinks = Stream.of(
                        new Link()
                                .setUrl("https://github.com")
                                .setLastUpdatedAt(OffsetDateTime.now()),
                        new Link()
                                .setUrl("https://vk.com")
                                .setLastUpdatedAt(OffsetDateTime.now()),
                        new Link()
                                .setUrl("https://stackoverflow.com")
                                .setLastUpdatedAt(OffsetDateTime.now())
                )
                .map(linkRepository::createAndReturn)
                .toList();
    }

    @BeforeEach
    @Transactional
    @Rollback
    public void initTelegramChats() {
        bindedChats = List.of(
                new TelegramChat()
                        .setId(123L)
                        .setRegisteredAt(OffsetDateTime.now()),
                new TelegramChat()
                        .setId(124L)
                        .setRegisteredAt(OffsetDateTime.now()),
                new TelegramChat()
                        .setId(125L)
                        .setRegisteredAt(OffsetDateTime.now())
        );
        bindedChats.forEach(telegramChatRepository::create);
        bindedChats = telegramChatRepository.getAll();
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateThrowsLinkAlreadyTrackedExceptionWithSameEntity() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Create Binding
        repository.create(new Binding(telegramChat, link));

        // Check
        assertThrows(LinkAlreadyTrackedException.class, () -> repository.create(new Binding(telegramChat, link)));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteThrowsLinkIsNotTrackedExceptionWithSameEntity() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Create Binding
        repository.create(new Binding(telegramChat, link));

        // Delete Binding
        repository.delete(new Binding(telegramChat, link));

        // Check
        assertThrows(LinkIsNotTrackedException.class, () -> repository.delete(new Binding(telegramChat, link)));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateIsNotAllowed() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Check
        assertThrows(IllegalStateException.class, () -> repository.update(new Binding(telegramChat, link)));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertIsNotAllowed() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Check
        assertThrows(IllegalStateException.class, () -> repository.upsert(new Binding(telegramChat, link)));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllChatsSubscribedToWithSingleLinkWorksRight() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Create Binding
        repository.create(new Binding(telegramChat, link));

        // Check
        List<TelegramChat> chats = repository.findAllChatsSubscribedTo(link);
        assertEquals(1, chats.size());
        assertEquals(telegramChat, chats.get(0));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllChatsSubscribedToWithMultiLinkWorksRight() {
        // Setup
        Link link = bindedLinks.get(0);

        // Create Bindings
        bindedChats.forEach(telegramChat -> repository.create(new Binding(telegramChat, link)));

        // Check
        List<TelegramChat> chats = repository.findAllChatsSubscribedTo(link);
        assertEquals(bindedChats, chats);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllLinksSubscribedWithSingleLinkWorksRight() {
        // Setup
        Link link = bindedLinks.get(0);
        TelegramChat telegramChat = bindedChats.get(0);

        // Create Binding
        repository.create(new Binding(telegramChat, link));

        // Check
        List<Link> chats = repository.findAllLinksSubscribedWith(telegramChat);
        assertEquals(1, chats.size());
        assertEquals(link, chats.get(0));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllLinksSubscribedWithMultiLinkWorksRight() {
        // Setup
        TelegramChat telegramChat = bindedChats.get(0);

        // Create Bindings
        bindedLinks.forEach(link -> repository.create(new Binding(telegramChat, link)));

        // Check
        List<Link> links = repository.findAllLinksSubscribedWith(telegramChat);
        assertEquals(bindedLinks, links);
    }
}
