package edu.java.scrapper.repository;

import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class TelegramChatRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private TelegramChatRepository repository;

    @Test
    @Transactional
    @Rollback
    public void assertThatGetWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.create(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Get List<TelegramChat>
        List<TelegramChat> chats = repository.getAll();
        assertFalse(chats.isEmpty());
        telegramChat = chats.get(0);

        // Check
        assertEquals(telegramChat, repository.get(telegramChat.getId()).orElseThrow());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatGetAllWorksRight() {
        // Setup
        List<TelegramChat> chats = List.of(
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
        assertTrue(repository.getAll().isEmpty());

        // Create List<TelegramChat>
        chats.forEach(repository::create);

        // Check
        assertEquals(chats.size(), repository.getAll().size());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.create(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Check
        TelegramChat createdTelegramChat = repository.get(telegramChat.getId()).orElseThrow();
        assertEquals(telegramChat.getId(), createdTelegramChat.getId());
        assertEquals(telegramChat.getRegisteredAt().toEpochSecond(), createdTelegramChat.getRegisteredAt().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateThrowsDataIntegrityViolationExceptionWithSameEntity() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.create(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Check
        assertThrows(DataIntegrityViolationException.class, () -> repository.create(telegramChat));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.create(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Check
        repository.delete(telegramChat);
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.create(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Update TelegramChat
        telegramChat = telegramChat.setRegisteredAt(OffsetDateTime.now().plusHours(5));
        repository.update(telegramChat);

        // Check
        TelegramChat updatedTelegramChat = repository.get(telegramChat.getId()).orElseThrow();
        assertEquals(telegramChat.getId(), updatedTelegramChat.getId());
        assertEquals(telegramChat.getRegisteredAt().toEpochSecond(), updatedTelegramChat.getRegisteredAt().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenInsertWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Upsert TelegramChat
        repository.upsert(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Check
        TelegramChat upsertedTelegramChat = repository.get(telegramChat.getId()).orElseThrow();
        assertEquals(telegramChat.getId(), upsertedTelegramChat.getId());
        assertEquals(telegramChat.getRegisteredAt().toEpochSecond(), upsertedTelegramChat.getRegisteredAt().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenUpdateWorksRight() {
        // Setup
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create TelegramChat
        repository.upsert(telegramChat);
        assertFalse(repository.getAll().isEmpty());

        // Upsert TelegramChat
        telegramChat = telegramChat.setRegisteredAt(OffsetDateTime.now().plusHours(5));
        repository.upsert(telegramChat);

        // Check
        TelegramChat updatedTelegramChat = repository.get(telegramChat.getId()).orElseThrow();
        assertEquals(telegramChat.getId(), updatedTelegramChat.getId());
        assertEquals(telegramChat.getRegisteredAt().toEpochSecond(), updatedTelegramChat.getRegisteredAt().toEpochSecond());
    }
}
