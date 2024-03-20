package edu.java.scrapper.data.db.impl.jdbc;

import edu.java.core.exception.UnknownException;
import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.core.exception.UserIsNotAuthorizedException;
import edu.java.scrapper.data.db.TelegramChatRepository;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.util.List;
import java.util.Optional;
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
    public void registerTelegramChat(TelegramChat telegramChat) throws UserAlreadyAuthorizedException {
        this.findById(telegramChat.getId())
                .ifPresentOrElse(
                        chat -> {
                            throw new UserAlreadyAuthorizedException(telegramChat.getId());
                        },
                        () -> {
                            try {
                                this.add(telegramChat).orElseThrow();
                            } catch (Exception exception) {
                                throw new UnknownException(this.getClass(), exception);
                            }
                        }
                );
    }

    @Override
    public void unregisterTelegramChat(TelegramChat telegramChat) throws UserIsNotAuthorizedException {
        this.removeById(telegramChat.getId()).orElseThrow(() -> new UserIsNotAuthorizedException(telegramChat.getId()));
    }
}
