package edu.java.domain;

import edu.java.domain.dto.LinkDTO;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinksDAO {
    JdbcClient jdbcClient;

    @Autowired
    public JdbcLinksDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public void add(String url) {
        String query = "INSERT INTO links(url) VALUES(?);";
        jdbcClient.sql(query).param(url).update();
    }

    @Transactional
    public void remove(String url) {
        String query = "DELETE FROM links WHERE url=?;";
        jdbcClient.sql(query).param(url).update();
    }

    //Нужна ли поддержка сериализации?, нужен ли Serializable?
    //Мне также вообще не нравится, что здесь я указываю явные имена стобцов, может их как-то можно инъектить?
    //Как я понимаю JdbcClient это не поддерживает?
    public List<LinkDTO> findAll() {
        String query = "SELECT * FROM links;";
        return jdbcClient.sql(query).query((rs, rowNum) ->
            new LinkDTO(rs.getLong("id"), rs.getString("url"),
                rs.getObject("checked_at", OffsetDateTime.class)
            )).list();
    }

    //Это ок, делать такой метод?
    public Long getId(String url) {
        String queryGetUrlId = "SELECT id FROM links WHERE url=?";
        return jdbcClient.sql(queryGetUrlId).param(url).query((rs, rowNum) ->
            rs.getLong("id")).single();
    }

    //Это ок, делать такой метод?
    public boolean contains(String url) {
        String query = "SELECT COUNT(*) FROM links WHERE url = ?";
        int count = jdbcClient.sql(query)
            .param(url)
            .query(Integer.class).single();
        return count > 0;
    }
}
