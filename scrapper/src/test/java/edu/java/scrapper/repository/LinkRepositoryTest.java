package edu.java.scrapper.repository;

import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class LinkRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddWorksRight() {
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(linkRepository.findAll().isEmpty());
        assertEquals(link.getUrl(), linkRepository.add(link).orElseThrow().getUrl());
        assertFalse(linkRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddAlreadyExistsEntityThrowsDuplicateKeyException() {
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        linkRepository.add(link);
        assertThrows(DuplicateKeyException.class, () -> linkRepository.add(link));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveWorksRight() {
        assertTrue(linkRepository.findAll().isEmpty());
        Link link = linkRepository
                .add(new Link().setUrl("https://github.com").setLastUpdatedAt(OffsetDateTime.now()))
                .orElseThrow();
        assertEquals(link.getUrl(), linkRepository.removeById(link.getId()).orElseThrow().getUrl());
        assertTrue(linkRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveNonExistsEntityReturnsNullableOptional() {
        Link link = new Link()
                .setId(1L)
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(linkRepository.findAll().isEmpty());
        assertTrue(linkRepository.removeById(link.getId()).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllWorksRight() {
        assertTrue(linkRepository.findAll().isEmpty());
        List<Link> links = List.of(
                new Link().setUrl("https://link1.ru").setLastUpdatedAt(OffsetDateTime.now()),
                new Link().setUrl("https://link2.ru").setLastUpdatedAt(OffsetDateTime.now())
        );
        links.forEach(linkRepository::add);
        assertEquals(links.size(), linkRepository.findAll().size());
    }
}
