package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<ChatDto> rowMapper = new DataClassRowMapper<>(ChatDto.class);

    public Iterable<ChatDto> findAll() {
        return jdbcTemplate.query("select * from chats", rowMapper);
    }

    @Transactional
    public void add(ChatDto chat) {
        jdbcTemplate.update(
            "insert into chats(registered_at) values(:registeredAt)",
            new BeanPropertySqlParameterSource(chat)
        );
    }

    @Transactional
    public int remove(ChatDto chat) {
        return jdbcTemplate.update(
            "delete from chats where chat_id = :chatId",
            new MapSqlParameterSource()
                .addValue("chatId", chat.getChatId())
        );
    }
}
