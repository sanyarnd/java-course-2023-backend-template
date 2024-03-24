package edu.java.scrapper.data.db.repository.impl.jpa.dao;

import edu.java.scrapper.data.db.entity.Binding;
import edu.java.scrapper.data.db.entity.TelegramChat;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BindingDao extends JpaRepository<Binding, Binding.BindingPK> {
    @Modifying(clearAutomatically = true)
    @Query(
            value = "INSERT INTO chat_to_link_binding (chat_id, link_id) VALUES (:chat_id, :link_id)",
            nativeQuery = true
    )
    int create(@Param("chat_id") Long chatId, @Param("link_id") Long linkId);

    @Modifying(clearAutomatically = true)
    @Query(
            value = "DELETE FROM chat_to_link_binding WHERE chat_id=:chat_id AND link_id=:link_id",
            nativeQuery = true
    )
    int delete(@Param("chat_id") Long chatId, @Param("link_id") Long linkId);

    List<Binding> findAllByLinkId(Long linkId);

    List<Binding> findAllByChatId(Long chatId);
}
