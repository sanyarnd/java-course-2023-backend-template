package edu.java.domain;

import edu.java.domain.dto.LinksDTO;
import edu.java.domain.dto.SubscribesDTO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class JdbcSubscribesDAO {
    JdbcClient jdbcClient;

    public JdbcSubscribesDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public void add(Long chatId, Long urlId) {
        //На каком этапе надо добавлять ссылку в таблицу ссылок, когда она добавляется в первый раз?
        //Сейчас это будет делаться у меня в сервисе

        String query = "INSERT INTO subscribes(chatId,linkId) VALUES(?,?);";
        jdbcClient.sql(query).param(chatId).param(urlId).update();
    }

    @Transactional
    public void remove(Long chatId, String url) {
        String queryGetUrlId = "SELECT id FROM links WHERE url=?";
        Long linkId = jdbcClient.sql(queryGetUrlId).param(url).query((rs, rowNum) ->
            rs.getLong("id")).single();

        String query = "DELETE FROM subscribes WHERE chatId=? AND linkId=?;";
        jdbcClient.sql(query).param(chatId).param(linkId).update();
    }

    //Нужна ли поддержка сериализации?, нужен ли Serializable?
    public List<SubscribesDTO> findAll() {
        String query = "SELECT * FROM subscribes;";
        return jdbcClient.sql(query).query((rs, rowNum) ->
            SubscribesDTO.builder().chatId(rs.getLong("chatId"))
                .linkId(rs.getLong("linkId")).build()).list();
    }
}
