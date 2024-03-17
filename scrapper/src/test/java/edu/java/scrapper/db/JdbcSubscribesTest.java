package edu.java.scrapper.db;

import edu.java.domain.JdbcChatsDAO;
import edu.java.domain.JdbcLinksDAO;
import edu.java.domain.JdbcSubscribesDAO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcSubscribesTest extends IntegrationTest {
    @Autowired
    private JdbcLinksDAO linkRepository;

    @Autowired
    private JdbcChatsDAO chatRepository;

    @Autowired
    private JdbcSubscribesDAO subscribeRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        linkRepository.add("java");
        chatRepository.add(1L);
        Long linkId = linkRepository.findAll().get(0).getId();

        subscribeRepository.add(1L, linkId);
        Assertions.assertEquals(1, subscribeRepository.findAll().size());
        Assertions.assertEquals(1L, subscribeRepository.findAll().get(0).getChatId());
        Assertions.assertEquals(
            linkId,
            subscribeRepository.findAll().get(0).getLinkId()
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        linkRepository.add("java");
        chatRepository.add(1L);
        Long linkId = linkRepository.findAll().get(0).getId();

        subscribeRepository.add(1L, linkId);
        Assertions.assertEquals(1, subscribeRepository.findAll().size());
        subscribeRepository.remove(1L, linkId);
        Assertions.assertEquals(0, subscribeRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void testForeignKeyChat() {
        linkRepository.add("java");
        chatRepository.add(1L);
        Long linkId = linkRepository.findAll().get(0).getId();

        subscribeRepository.add(1L, linkId);
        Assertions.assertEquals(1, subscribeRepository.findAll().size());
        chatRepository.remove(1L);
        Assertions.assertEquals(0, subscribeRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void testFindById() {
        //TODO
    }
}
