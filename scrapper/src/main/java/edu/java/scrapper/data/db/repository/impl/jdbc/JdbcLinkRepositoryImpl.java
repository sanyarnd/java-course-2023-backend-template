package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcLinkRepositoryImpl implements LinkRepository {
    private final JdbcClient client;

    public JdbcLinkRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<Link> get(Long entityId) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE id=:id")
                .param("id", entityId)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public List<Link> getAll() {
        return client.sql("SELECT id, url, last_updated_at FROM link")
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Link createAndReturn(Link entity) {
        return client.sql("INSERT INTO link (url, last_updated_at) VALUES (:url, :last_updated_at) RETURNING *")
                .param("url", entity.getUrl())
                .param("last_updated_at", entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Optional<Link> deleteAndReturn(Link entity) {
        return client.sql("DELETE from link WHERE id=:id AND url=:url AND last_updated_at=:last_updated_at RETURNING *")
                .param("id", entity.getId())
                .param("url", entity.getUrl())
                .param("last_updated_at", entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public Optional<Link> updateAndReturn(Link entity) {
        return client.sql("UPDATE link SET url=:url, last_updated_at=:last_updated_at WHERE id=:id RETURNING *")
                .param("id", entity.getId())
                .param("url", entity.getUrl())
                .param("last_updated_at", entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public Link upsertAndReturn(Link entity) {
        return client.sql("INSERT INTO link (url, last_updated_at) VALUES (:url, :last_updated_at) ON CONFLICT (url) DO UPDATE SET last_updated_at=:last_updated_at RETURNING *")
                .param("id", entity.getId())
                .param("url", entity.getUrl())
                .param("last_updated_at", entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<Link> getAllUpdatedBefore(OffsetDateTime timeBefore) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE last_updated_at < :last_updated_at")
                .param("last_updated_at", timeBefore)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Optional<Link> getByUrl(String url) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE url=:url")
                .param("url", url)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }
}
