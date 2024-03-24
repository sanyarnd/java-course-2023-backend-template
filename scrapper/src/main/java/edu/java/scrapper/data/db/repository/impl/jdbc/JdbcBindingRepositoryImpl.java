package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.core.exception.LinkAlreadyTrackedException;
import edu.java.core.exception.LinkIsNotTrackedException;
import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.BindingRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
public class JdbcBindingRepositoryImpl implements BindingRepository {
    private final static String NOT_SUPPORTED_DESCRIPTION = "This method is not supported!";
    private final static String CHAT_ID = "chat_id";
    private final static String LINK_ID = "link_id";
    private final static String FIND_CHATS_SUBSCRIBED_TO_LINK_ID = "SELECT telegram_chat.id, "
            + "telegram_chat.registered_at FROM telegram_chat INNER JOIN chat_to_link_binding "
            + "ON telegram_chat.id = chat_to_link_binding.chat_id AND chat_to_link_binding.link_id = :link_id";
    private final static String FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID = "SELECT link.id, link.url, link.last_updated_at "
            + "FROM link INNER JOIN chat_to_link_binding "
            + "ON link.id = chat_to_link_binding.link_id AND chat_to_link_binding.chat_id = :chat_id";

    private final JdbcClient client;

    @Override
    public void create(Binding entity) throws LinkAlreadyTrackedException {
        try {
            client.sql("INSERT INTO chat_to_link_binding (chat_id, link_id) VALUES (:chat_id, :link_id)")
                    .param(CHAT_ID, entity.getChatId())
                    .param(LINK_ID, entity.getLinkId())
                    .update();
        } catch (DataIntegrityViolationException exception) {
            throw new LinkAlreadyTrackedException(entity.getLinkId(), entity.getChatId());
        }
    }

    @Override
    public void delete(Binding entity) throws LinkIsNotTrackedException {
        int rowsAffected = client.sql("DELETE FROM chat_to_link_binding WHERE chat_id=:chat_id AND link_id=:link_id")
                .param(CHAT_ID, entity.getChatId())
                .param(LINK_ID, entity.getLinkId())
                .update();
        if (rowsAffected == 0) {
            throw new LinkIsNotTrackedException(entity.getLinkId(), entity.getChatId());
        }
    }

    @Override
    public void update(Binding entity) throws IllegalStateException {
        throw new IllegalStateException(NOT_SUPPORTED_DESCRIPTION);
    }

    @Override
    public void upsert(Binding entity) throws IllegalStateException {
        throw new IllegalStateException(NOT_SUPPORTED_DESCRIPTION);
    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return client.sql(FIND_CHATS_SUBSCRIBED_TO_LINK_ID)
                .param(LINK_ID, link.getId())
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }

    @Override
    public List<Link> findAllLinksSubscribedWith(TelegramChat chat) {
        return client.sql(FIND_LINKS_SUBSCRIBED_WITH_CHAT_ID)
                .param(CHAT_ID, chat.getId())
                .query(new BeanPropertyRowMapper<>(Link.class))
                .list();
    }
}
