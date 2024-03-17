package edu.java.scrapper.db;

import edu.java.domain.JdbcLinksDAO;
import edu.java.domain.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
public class JdbcLinksTest extends IntegrationTest {
    @Autowired
    private JdbcLinksDAO linkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        linkRepository.add("http://java.com");
        Assertions.assertEquals("http://java.com", linkRepository.findAll().get(0).getUrl());
        Assertions.assertEquals(1, linkRepository.findAll().size());

        linkRepository.add("http://kotlin.com");
        List<LinkDTO> links = linkRepository.findAll();
        Assertions.assertEquals(2, links.size());
        Assertions.assertTrue("http://kotlin.com".equals(links.get(0).getUrl())
            || "http://kotlin.com".equals(links.get(1).getUrl()));
        Assertions.assertTrue("http://java.com".equals(links.get(0).getUrl())
            || "http://kotlin.com".equals(links.get(1).getUrl()));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        linkRepository.add("http://java.com");
        linkRepository.add("http://kotlin.com");

        List<LinkDTO> links = linkRepository.findAll();

        Assertions.assertEquals(2, links.size());

        linkRepository.remove("http://java.com");
        Assertions.assertEquals(1, linkRepository.findAll().size());
        linkRepository.remove("http://kotlin.com");

        links = linkRepository.findAll();
        Assertions.assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void containsTest() {
        //TODO
    }

    @Test
    @Transactional
    @Rollback
    void getIdTest() {
        //TODO
    }
}
