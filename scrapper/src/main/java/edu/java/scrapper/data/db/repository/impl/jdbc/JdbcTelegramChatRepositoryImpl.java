package edu.java.scrapper.data.db.repository.impl.jdbc;

import edu.java.core.exception.UserAlreadyAuthorizedException;
import edu.java.scrapper.data.db.entity.TelegramChat;
import edu.java.scrapper.data.db.repository.TelegramChatRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JdbcTelegramChatRepositoryImpl implements TelegramChatRepository {
    private final JdbcClient client;

    public JdbcTelegramChatRepositoryImpl(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Optional<TelegramChat> get(Long entityId) {
        return client.sql("SELECT id, registered_at FROM telegram_chat WHERE id=:id")
                .param("id", entityId)
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .optional();
    }

    @Override
    public List<TelegramChat> getAll() {
        return client.sql("SELECT id, registered_at FROM telegram_chat")
                .query(new BeanPropertyRowMapper<>(TelegramChat.class))
                .list();
    }

    @Override
    public void create(TelegramChat entity) throws UserAlreadyAuthorizedException {
        client.sql("INSERT INTO telegram_chat (id, registered_at) VALUES (:id, :registered_at)")
                .param("id", entity.getId())
                .param("registered_at", entity.getRegisteredAt())
                .update();
    }

    @Override
    public void delete(TelegramChat entity) throws UserAlreadyAuthorizedException {
        client.sql("DELETE from telegram_chat WHERE id=:id AND registered_at=:registered_at")
                .param("id", entity.getId())
                .param("registered_at", entity.getRegisteredAt())
                .update();
    }

    @Override
    public void update(TelegramChat entity) {
        client.sql("UPDATE telegram_chat SET registered_at=:registered_at WHERE id=:id")
                .param("id", entity.getId())
                .param("registered_at", entity.getRegisteredAt())
                .update();
    }

    @Override
    public void upsert(TelegramChat entity) {
        client.sql("INSERT INTO telegram_chat (id, registered_at) VALUES (:id, :registered_at) ON CONFLICT (id) DO UPDATE SET registered_at=:registered_at")
                .param("id", entity.getId())
                .param("registered_at", entity.getRegisteredAt())
                .update();
    }
}
