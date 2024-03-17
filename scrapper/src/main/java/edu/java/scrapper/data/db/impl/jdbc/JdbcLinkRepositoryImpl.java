package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.core.exception.link.LinkAlreadyRegistered;
import edu.java.scrapper.data.db.LinkRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
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
    private static final String ADD_CHAT_TO_LINK = "INSERT INTO chat_to_link_binding (chat_id, link_id) VALUES (?, ?)";
    private static final String DELETE_CHAT_TO_LINK = "DELETE FROM chat_to_link_binding WHERE chat_id=? AND link_id=?";
    private static final String UPDATE_LINK = "UPDATE link SET last_updated_at=? WHERE id=? RETURNING *";
    private static final String FIND_BY_URL = "SELECT * from link WHERE url=?";
    private static final String FIND_BY_DATE = "SELECT * FROM link WHERE last_updated_at < ?";
    private final static String FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID = "SELECT link.id, link.url, link.last_updated_at "
            + "FROM link INNER JOIN chat_to_link_binding "
            + "ON link.id = chat_to_link_binding.link_id AND chat_to_link_binding.chat_id=?";

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
    public Link registerLink(Link link) throws LinkAlreadyRegistered {
        try {
            return this.findByUrl(link.getUrl()).orElse(this.add(link).orElseThrow());
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new LinkAlreadyRegistered();
        }
    }

    @Override
    public void update(Link link) {
        client.sql(UPDATE_LINK)
                .param(link.getLastUpdatedAt())
                .param(link.getId())
                .query();
    }

    @Override
    public void subscribe(Link link, TelegramChat telegramChat) {
        client.sql(ADD_CHAT_TO_LINK)
                .param(telegramChat.getId())
                .param(link.getId())
                .query();
    }

    @Override
    public void unsubscribe(Link link, TelegramChat telegramChat) {
        client.sql(DELETE_CHAT_TO_LINK)
                .param(telegramChat.getId())
                .param(link.getId())
                .query();
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat chat) {
        return client.sql(FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID)
                .param(chat.getId())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public List<Link> findAllLinksUpdatedBefore(OffsetDateTime offsetDateTime) {
        return client.sql(FIND_BY_DATE)
                .param(offsetDateTime)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }

    @Override
    public Optional<Link> findByUrl(URL url) {
        return client.sql(FIND_BY_URL)
                .param(url)
                .query(new BeanPropertyRowMapper<>(Link.class))
                .optional();
    }
}
