package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.core.exception.UserAlreadyRegistered;
import edu.java.core.exception.UserNotRegistered;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.Link;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcTelegramChatRepositoryImpl implements TelegramChatRepository {
    private final static String ADD = "INSERT INTO telegram_chat (id, registered_at) VALUES (?, ?) RETURNING *";
    private final static String DELETE = "DELETE FROM telegram_chat WHERE id=? RETURNING *";
    private final static String FIND_BY_ID = "SELECT * FROM telegram_chat WHERE id=?";
    private final static String FIND_ALL = "SELECT * FROM telegram_chat";
    @SuppressWarnings("LineLength")
    private final static String FIND_CHATS_SUBSCRIBED_TO_LINK_ID = "SELECT telegram_chat.id, telegram_chat.registered_at "
            + "FROM telegram_chat INNER JOIN chat_to_link_binding "
            + "ON telegram_chat.id = chat_to_link_binding.chat_id AND chat_to_link_binding.link_id = ?";

    private final JdbcClient client;

    public JdbcTelegramChatRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<TelegramChat> add(TelegramChat entity) {
        return client.sql(ADD)
                .param(entity.getId())
                .param(entity.getRegisteredAt())
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .optional();
    }

    @Override
    public Optional<TelegramChat> removeById(Long id) {
        return client.sql(DELETE)
                .param(id)
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .optional();
    }

    @Override
    public Optional<TelegramChat> findById(Long id) {
        return client.sql(FIND_BY_ID)
                .param(id)
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .optional();
    }

    @Override
    public List<TelegramChat> findAll() {
        return client.sql(FIND_ALL)
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }

    @Override
    public void registerTelegramChat(TelegramChat telegramChat) throws UserAlreadyRegistered {
        try {
            this.add(telegramChat).orElseThrow(UserAlreadyRegistered::new);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new UserAlreadyRegistered();
        }
    }

    @Override
    public void unregisterTelegramChat(TelegramChat telegramChat) throws UserNotRegistered {
        this.removeById(telegramChat.getId()).orElseThrow(UserNotRegistered::new);
    }

    @Override
    public List<TelegramChat> findAllChatsSubscribedTo(Link link) {
        return client.sql(FIND_CHATS_SUBSCRIBED_TO_LINK_ID)
                .param(link.getId())
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }
}
