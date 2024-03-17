package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = new DataClassRowMapper<>(LinkDto.class);

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Iterable<LinkDto> findAll() {
        return jdbcTemplate.query("select * from links", rowMapper);
    }

    @Transactional
    public void add(LinkDto link) {
        jdbcTemplate.update(
            "insert into links(url, updated_at) values(:url, :updatedAt)",
            new BeanPropertySqlParameterSource(link)
        );
    }

    @Transactional
    public int remove(LinkDto link) {
        return jdbcTemplate.update(
            "delete from links where link_id = :linkId",
            new MapSqlParameterSource()
                .addValue("linkId", link.getLinkId())
        );
    }

    public void map(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "insert into links_to_chats(chat_id, link_id) values(:chatId, :linkId)",
            new MapSqlParameterSource()
                .addValue("linkId", link.getLinkId())
                .addValue("chatId", chat.getChatId())
        );
    }

    public void unmap(LinkDto link, ChatDto chat) {
        jdbcTemplate.update(
            "delete from links_to_chats where link_id = :linkId and chat_id = :chatId",
            new MapSqlParameterSource()
                .addValue("linkId", link.getLinkId())
                .addValue("chatId", chat.getChatId())
        );
    }
}
