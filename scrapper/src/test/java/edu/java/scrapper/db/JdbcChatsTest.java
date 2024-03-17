package edu.java.scrapper.db;

import edu.java.domain.JdbcChatsDAO;
import edu.java.domain.dto.ChatDTO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
public class JdbcChatsTest extends IntegrationTest {
    @Autowired
    private JdbcChatsDAO chatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        chatRepository.add(1L);
        Assertions.assertEquals(1L, chatRepository.findAll().get(0).getTelegramId());
        Assertions.assertEquals(1, chatRepository.findAll().size());

        chatRepository.add(2L);
        List<ChatDTO> chats = chatRepository.findAll();
        Assertions.assertEquals(2, chats.size());
        Assertions.assertTrue(1L == (chats.get(0).getTelegramId())
            || 1L == (chats.get(1).getTelegramId()));
        Assertions.assertTrue(2L == (chats.get(0).getTelegramId())
            || 2L == (chats.get(1).getTelegramId()));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        chatRepository.add(1L);
        chatRepository.add(2L);

        List<ChatDTO> chats = chatRepository.findAll();

        Assertions.assertEquals(2, chats.size());

        chatRepository.remove(1L);
        Assertions.assertEquals(1, chatRepository.findAll().size());
        chatRepository.remove(2L);

        chats = chatRepository.findAll();
        Assertions.assertTrue(chats.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void containsTest() {
        //TODO
    }
}
