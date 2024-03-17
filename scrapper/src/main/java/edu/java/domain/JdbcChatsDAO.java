package edu.java.domain;

import edu.java.domain.dto.ChatsDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class JdbcChatsDAO {

    JdbcClient jdbcClient;

    public JdbcChatsDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public void add(Long telegramId) {
        String query = "INSERT INTO chats(telegramId) VALUES(?);";
        jdbcClient.sql(query).param(telegramId).update();
    }

    @Transactional
    public void remove(Long telegramId) {
        String query = "DELETE FROM chats WHERE telegramId=?;";
        jdbcClient.sql(query).param(telegramId).update();
    }

    //Нужна ли поддержка сериализации?, нужен ли Serializable?
    public List<ChatsDTO> findAll() {
        String query = "SELECT * FROM chats;";
        return jdbcClient.sql(query).query((rs, rowNum) ->
            new ChatsDTO(rs.getLong("telegramId"))).list();
    }
}
