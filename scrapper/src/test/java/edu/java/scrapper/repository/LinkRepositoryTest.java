package edu.java.scrapper.repository;

import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
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
public class LinkRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private LinkRepository repository;

    @Test
    @Transactional
    @Rollback
    public void assertThatGetWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create Link
        repository.createAndReturn(link);
        assertFalse(repository.getAll().isEmpty());

        // Get List<Link>
        List<Link> links = repository.getAll();
        assertFalse(links.isEmpty());
        link = links.get(0);

        // Check equals
        assertEquals(link, repository.get(link.getId()).orElseThrow());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatGetAllWorksRight() {
        // Setup
        List<Link> links = List.of(
                new Link()
                        .setUrl("https://github.com")
                        .setLastUpdatedAt(OffsetDateTime.now()),
                new Link()
                        .setUrl("https://vk.com")
                        .setLastUpdatedAt(OffsetDateTime.now()),
                new Link()
                        .setUrl("https://stackoverflow.com")
                        .setLastUpdatedAt(OffsetDateTime.now())
        );
        assertTrue(repository.getAll().isEmpty());

        // Create List<Link>
        links.forEach(repository::createAndReturn);

        // Check
        assertEquals(links.size(), repository.getAll().size());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create Link
        Link createdLink = repository.createAndReturn(link);

        // Check
        assertFalse(repository.getAll().isEmpty());
        assertEquals(link.getUrl(), createdLink.getUrl());
        assertEquals(link.getLastUpdatedAt().toEpochSecond(), createdLink.getLastUpdatedAt().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateThrowsDataIntegrityViolationExceptionWithSameEntity() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Create Link
        repository.createAndReturn(link);

        // Check
        assertThrows(DataIntegrityViolationException.class, () -> repository.createAndReturn(link));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());
        link = repository.createAndReturn(link);

        // Create Link
        Optional<Link> deletedLink = repository.deleteAndReturn(link);

        // Check
        assertEquals(link, deletedLink.orElseThrow());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());
        link = repository.createAndReturn(link);

        // Update link
        link = link.setUrl("https://vk.com");
        Optional<Link> updatedLink = repository.updateAndReturn(link);

        // Check
        assertEquals(link, updatedLink.orElseThrow());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenInsertWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());

        // Upsert Link
        Link upsertedLink = repository.upsertAndReturn(link);
        assertFalse(repository.getAll().isEmpty());

        // Check
        assertNotNull(upsertedLink.getId());
        assertEquals(link.getUrl(), upsertedLink.getUrl());
        assertEquals(link.getLastUpdatedAt().toEpochSecond(), upsertedLink.getLastUpdatedAt().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenUpdateWorksRight() {
        // Setup
        Link link = new Link()
                .setUrl("https://github.com")
                .setLastUpdatedAt(OffsetDateTime.now());
        assertTrue(repository.getAll().isEmpty());
        link = repository.createAndReturn(link);

        // Upsert Link
        link = link.setLastUpdatedAt(OffsetDateTime.now().plusHours(5));
        Link upsertedLink = repository.upsertAndReturn(link);

        // Check
        assertNotNull(upsertedLink.getId());
        assertEquals(link.getUrl(), upsertedLink.getUrl());
        assertEquals(link.getLastUpdatedAt().toEpochSecond(), upsertedLink.getLastUpdatedAt().toEpochSecond());
    }
}
