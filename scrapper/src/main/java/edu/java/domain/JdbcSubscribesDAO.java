package edu.java.domain;

import edu.java.domain.dto.SubscribeDTO;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcSubscribesDAO {
    JdbcClient jdbcClient;
    private final String linkIdColumnName = "linkId";
    private final String chatIdColumnName = "chatId";

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
    public void remove(Long chatId, Long urlId) {
        String query = "DELETE FROM subscribes WHERE chatId=? AND linkId=?;";
        jdbcClient.sql(query).param(chatId).param(urlId).update();
    }

    //Нужна ли поддержка сериализации?, нужен ли Serializable?
    public List<SubscribeDTO> findAll() {
        String query = "SELECT * FROM subscribes;";
        return jdbcClient.sql(query).query((rs, rowNum) ->
            SubscribeDTO.builder().chatId(rs.getLong(chatIdColumnName))
                .linkId(rs.getLong(linkIdColumnName)).build()).list();
    }

    //Это ок, делать такой метод? Просто кажется,
    // лучше что пусть лучше бд будет обрабатывать это,
    // чем если сервер будет сначала принимать
    // все записи, а потом фильтровать их
    public List<SubscribeDTO> findAll(Long chatId) {
        String query = "SELECT * FROM subscribes WHERE chatId=?;";
        return jdbcClient.sql(query).param(chatId).query((rs, rowNum) ->
            SubscribeDTO.builder().chatId(rs.getLong(chatIdColumnName))
                .linkId(rs.getLong(linkIdColumnName)).build()).list();
    }
}
