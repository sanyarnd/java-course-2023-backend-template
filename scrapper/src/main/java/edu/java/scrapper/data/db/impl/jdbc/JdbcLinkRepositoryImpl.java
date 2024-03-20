package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.entity.Link;
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
    private final static String ADD = "INSERT INTO link (url, last_updated_at) VALUES (?, ?) RETURNING *";
    private final static String DELETE = "DELETE FROM link WHERE id=? RETURNING *";
    private final static String FIND_BY_ID = "SELECT * FROM link WHERE id=?";
    private final static String FIND_ALL = "SELECT * FROM link";
    private static final String UPDATE_LINK = "UPDATE link SET last_updated_at=? WHERE id=?";
    private static final String FIND_BY_URL = "SELECT * from link WHERE url=?";
    private static final String FIND_BY_DATE = "SELECT * FROM link WHERE last_updated_at < ?";

    private final JdbcClient client;

    public JdbcLinkRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<Link> add(Link entity) {
        return client.sql(ADD)
                .param(entity.getUrl())
                .param(entity.getLastUpdatedAt())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public Optional<Link> removeById(Long id) {
        return client.sql(DELETE)
                .param(id)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public Optional<Link> findById(Long id) {
        return client.sql(FIND_BY_ID)
                .param(id)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }

    @Override
    public List<Link> findAll() {
        return client.sql(FIND_ALL)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Link addOrGetExisted(Link link) {
        return this.findByUrl(link.getUrl()).orElseGet(() -> this.add(link).orElseThrow(IllegalStateException::new));
    }

    @Override
    public void updateTimeTouched(Link link) {
        client.sql(UPDATE_LINK)
                .param(link.getLastUpdatedAt())
                .param(link.getId())
                .update();
    }

    @Override
    public List<Link> findAllLinksUpdatedBefore(OffsetDateTime offsetDateTime) {
        return client.sql(FIND_BY_DATE)
                .param(offsetDateTime)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Optional<Link> findByUrl(String url) {
        return client.sql(FIND_BY_URL)
                .param(url)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }
}
