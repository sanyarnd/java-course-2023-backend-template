package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.scrapper.data.db.LinkContentRepository;
import edu.java.scrapper.data.db.entity.LinkContent;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcLinkLinkContentRepositoryImpl implements LinkContentRepository {
    private final static String ADD = "INSERT INTO link_content (id, raw, hash) VALUES (?, ?, ?) RETURNING *";
    private final static String DELETE = "DELETE FROM link_content WHERE id=? RETURNING *";
    private final static String FIND_BY_ID = "SELECT * FROM link_content WHERE id=?";
    private final static String FIND_ALL = "SELECT * FROM link_content";
    private static final String UPDATE_LINK_CONTENT = "UPDATE link_content SET raw=?, hash=? WHERE id=? RETURNING *";


    private final JdbcClient client;

    public JdbcLinkLinkContentRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<LinkContent> add(LinkContent entity) {
        return client.sql(ADD)
                .param(entity.getId())
                .param(entity.getRaw())
                .param(entity.getHash())
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .optional();
    }

    @Override
    public Optional<LinkContent> removeById(Long id) {
        return client.sql(DELETE)
                .param(id)
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .optional();
    }

    @Override
    public Optional<LinkContent> findById(Long id) {
        return client.sql(FIND_BY_ID)
                .param(id)
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .optional();
    }

    @Override
    public List<LinkContent> findAll() {
        return client.sql(FIND_ALL)
                .query(new BeanPropertyRowMapper<>(LinkContent.class))
                .list();
    }

    @Override
    public void update(LinkContent linkContent) {
        client.sql(UPDATE_LINK_CONTENT)
                .param(linkContent.getRaw())
                .param(linkContent.getHash())
                .param(linkContent.getId())
                .query();
    }
}
