package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
public class JdbcLinkRepositoryImpl implements LinkRepository {
    private final static String ID = "id";
    private final static String URL = "url";
    private final static String LAST_UPDATED_AT = "last_updated_at";

    private final JdbcClient client;

    @Override
    public Optional<Link> get(Long entityId) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE id=:id")
                .param(ID, entityId)
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
                .param(URL, entity.getUrl())
                .param(LAST_UPDATED_AT, entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Optional<Link> deleteAndReturn(Link entity) {
        return client.sql("DELETE from link WHERE id=:id AND url=:url AND last_updated_at=:last_updated_at RETURNING *")
                .param(ID, entity.getId())
                .param(URL, entity.getUrl())
                .param(LAST_UPDATED_AT, entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public Optional<Link> updateAndReturn(Link entity) {
        return client.sql("UPDATE link SET url=:url, last_updated_at=:last_updated_at WHERE id=:id RETURNING *")
                .param(ID, entity.getId())
                .param(URL, entity.getUrl())
                .param(LAST_UPDATED_AT, entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @SuppressWarnings("LineLength")
    @Override
    public Link upsertAndReturn(Link entity) {
        return client.sql("INSERT INTO link (url, last_updated_at) VALUES (:url, :last_updated_at) ON CONFLICT (url) DO UPDATE SET last_updated_at=:last_updated_at RETURNING *")
                .param(ID, entity.getId())
                .param(URL, entity.getUrl())
                .param(LAST_UPDATED_AT, entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<Link> getAllUpdatedBefore(OffsetDateTime timeBefore) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE last_updated_at < :last_updated_at")
                .param(LAST_UPDATED_AT, timeBefore)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Optional<Link> getByUrl(String url) {
        return client.sql("SELECT id, url, last_updated_at FROM link WHERE url=:url")
                .param(URL, url)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }
}
