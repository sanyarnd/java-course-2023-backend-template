package edu.java.scrapper.repository;

import edu.java.scrapper.PostgresIntegrationTest;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.LinkContent;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class LinkContentRepositoryTest extends PostgresIntegrationTest {
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private LinkContentRepository repository;

    private List<Link> bindedLinks;

    @BeforeEach
    @Transactional
    @Rollback
    public void setup() {
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

    @Test
    @Transactional
    @Rollback
    public void assertThatGetWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Get List<LinkContent>
        List<LinkContent> contents = repository.getAll();
        assertFalse(contents.isEmpty());
        content = contents.get(0);

        // Check
        assertEquals(content, repository.get(content.getId()).orElseThrow());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatGetAllWorksRight() {
        // Setup
        List<LinkContent> contents = List.of(
                new LinkContent()
                        .setId(bindedLinks.get(0).getId())
                        .setRaw("{ a: 1, b: 2, c: null }")
                        .setHash(5),
                new LinkContent()
                        .setId(bindedLinks.get(1).getId())
                        .setRaw("{ a: 2, b: 3, c: 4 }")
                        .setHash(67),
                new LinkContent()
                        .setId(bindedLinks.get(2).getId())
                        .setRaw("{ a: null, b: 2, c: null }")
                        .setHash(22)
        );
        assertTrue(repository.getAll().isEmpty());

        // Create List<LinkContent>
        contents.forEach(repository::create);

        // Check
        assertEquals(contents.size(), repository.getAll().size());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Check
        LinkContent createdLinkContent = repository.get(bindedLink.getId()).orElseThrow();
        assertEquals(content, createdLinkContent);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatCreateThrowsDataIntegrityViolationExceptionWithSameEntity() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Check
        assertThrows(DataIntegrityViolationException.class, () -> repository.create(content));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Check
        repository.delete(content);
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Update LinkContent
        content = content.setRaw("{ a: 2, b: 3, c: 4 }").setHash(67);
        repository.update(content);

        // Check
        LinkContent updatedLinkContent = repository.get(bindedLink.getId()).orElseThrow();
        assertEquals(content, updatedLinkContent);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenInsertWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Upsert LinkContent
        repository.upsert(content);
        assertFalse(repository.getAll().isEmpty());

        // Check
        LinkContent upsertedContent = repository.get(content.getId()).orElseThrow();
        assertEquals(content, upsertedContent);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpsertWhenUpdateWorksRight() {
        // Setup
        Link bindedLink = bindedLinks.get(0);
        LinkContent content = new LinkContent()
                .setId(bindedLink.getId())
                .setRaw("{ a: 1, b: 2, c: null }")
                .setHash(44);
        assertTrue(repository.getAll().isEmpty());

        // Create LinkContent
        repository.create(content);
        assertFalse(repository.getAll().isEmpty());

        // Upsert LinkContent
        content = content.setRaw("{ a: null, b: 2, c: null }").setHash(22);
        repository.upsert(content);

        // Check
        LinkContent upsertedContent = repository.get(content.getId()).orElseThrow();
        assertEquals(content, upsertedContent);
    }
}
