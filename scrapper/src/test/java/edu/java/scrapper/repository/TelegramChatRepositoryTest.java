package edu.java.scrapper.repository;

import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TelegramChatRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private TelegramChatRepository telegramChatRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddWorksRight() {
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(telegramChatRepository.findAll().isEmpty());
        telegramChatRepository.add(telegramChat);
        assertFalse(telegramChatRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddAlreadyExistsEntityThrowsDuplicateKeyException() {
        TelegramChat telegramChat = new TelegramChat().setId(1L).setRegisteredAt(OffsetDateTime.now());
        telegramChatRepository.add(telegramChat);
        assertThrows(DuplicateKeyException.class, () -> telegramChatRepository.add(telegramChat));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveWorksRight() {
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(telegramChatRepository.findAll().isEmpty());
        telegramChat = telegramChatRepository.add(telegramChat).orElseThrow();
        telegramChatRepository.removeById(telegramChat.getId());
        assertTrue(telegramChatRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveNonExistsEntityReturnsNullableOptional() {
        TelegramChat telegramChat = new TelegramChat()
                .setId(123L)
                .setRegisteredAt(OffsetDateTime.now());
        assertTrue(telegramChatRepository.findAll().isEmpty());
        assertTrue(telegramChatRepository.removeById(telegramChat.getId()).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllWorksRight() {
        assertTrue(telegramChatRepository.findAll().isEmpty());
        List<TelegramChat> chats = List.of(
                new TelegramChat().setId(1L).setRegisteredAt(OffsetDateTime.now()),
                new TelegramChat().setId(2L).setRegisteredAt(OffsetDateTime.now())
        );
        chats.forEach(telegramChatRepository::add);
        assertEquals(chats.size(), telegramChatRepository.findAll().size());
    }
}
