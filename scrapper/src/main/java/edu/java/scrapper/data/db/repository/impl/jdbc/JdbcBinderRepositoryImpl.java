package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BinderRepository;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcBinderRepositoryImpl implements BinderRepository {
    private final static String FIND_CHATS_SUBSCRIBED_TO_LINK_ID = "SELECT telegram_chat.id, "
            + "telegram_chat.registered_at FROM telegram_chat INNER JOIN chat_to_link_binding "
            + "ON telegram_chat.id = chat_to_link_binding.chat_id AND chat_to_link_binding.link_id = :link_id";
    private final static String FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID = "SELECT link.id, link.url, link.last_updated_at "
            + "FROM link INNER JOIN chat_to_link_binding "
            + "ON link.id = chat_to_link_binding.link_id AND chat_to_link_binding.chat_id = :chat_id";

    private final JdbcClient client;

    public JdbcBinderRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public void create(Pair<TelegramChat, Link> entity) throws LinkAlreadyTrackedException {
        try {
            client.sql("INSERT INTO chat_to_link_binding (chat_id, link_id) VALUES (:chat_id, :link_id)")
                    .param("chat_id", entity.getLeft().getId())
                    .param("link_id", entity.getRight().getId())
                    .update();
        } catch (DataIntegrityViolationException exception) {
            throw new LinkAlreadyTrackedException(entity.getLeft().getId(), entity.getRight().getId());
        }
    }

    @Override
    public void delete(Pair<TelegramChat, Link> entity) throws LinkIsNotTrackedException {
        int rowsAffected = client.sql("DELETE FROM chat_to_link_binding WHERE chat_id=:chat_id AND link_id=:link_id")
                .param("chat_id", entity.getLeft().getId())
                .param("link_id", entity.getRight().getId())
                .update();
        if (rowsAffected == 0) {
            throw new LinkIsNotTrackedException(entity.getRight().getUrl());
        }
    }

    @Override
    public void update(Pair<TelegramChat, Link> entity) throws IllegalStateException {
        throw new IllegalStateException("This method is not supported!");
    }

    @Override
    public void upsert(Pair<TelegramChat, Link> entity) throws IllegalStateException {
        throw new IllegalStateException("This method is not supported!");
    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return client.sql(FIND_CHATS_SUBSCRIBED_TO_LINK_ID)
                .param("link_id", link.getId())
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat chat) {
        return client.sql(FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID)
                .param("chat_id", chat.getId())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }
}
