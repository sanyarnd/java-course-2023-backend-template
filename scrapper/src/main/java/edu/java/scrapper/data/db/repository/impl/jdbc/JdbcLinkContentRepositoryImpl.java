package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.LinkContentRepository;
import edu.java.scrapper.data.db.entity.LinkContent;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcLinkContentRepositoryImpl implements LinkContentRepository {
    private final JdbcClient client;

    public JdbcLinkContentRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<LinkContent> get(Long entityId) {
        return client.sql("SELECT id, raw, hash FROM link_content WHERE id=:id")
                .param("id", entityId)
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .optional();
    }

    @Override
    public List<LinkContent> getAll() {
        return client.sql("SELECT id, raw, hash FROM link_content")
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .list();
    }

    @Override
    public void create(LinkContent entity) throws DataIntegrityViolationException {
        client.sql("INSERT INTO link_content (id, raw, hash) VALUES (:id, :raw, :hash)")
                .param("id", entity.getId())
                .param("raw", entity.getRaw())
                .param("hash", entity.getHash())
                .update();
    }

    @Override
    public void delete(LinkContent entity) {
        client.sql("DELETE from link_content WHERE id=:id AND raw=:raw AND hash=:hash")
                .param("id", entity.getId())
                .param("raw", entity.getRaw())
                .param("hash", entity.getHash())
                .update();
    }

    @Override
    public void update(LinkContent entity) {
        client.sql("UPDATE link_content SET raw=:raw, hash=:hash WHERE id=:id")
                .param("id", entity.getId())
                .param("raw", entity.getRaw())
                .param("hash", entity.getHash())
                .update();
    }

    @Override
    public void upsert(LinkContent entity) {
        client.sql("INSERT INTO link_content (id, raw, hash) VALUES (:id, :raw, :hash) ON CONFLICT (id) DO UPDATE SET raw=:raw, hash=:hash")
                .param("id", entity.getId())
                .param("raw", entity.getRaw())
                .param("hash", entity.getHash())
                .update();
    }
}
