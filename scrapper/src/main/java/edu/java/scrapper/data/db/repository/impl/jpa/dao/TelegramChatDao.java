package edu.java.scrapper.data.db.repository.impl.jpa.dao;

import edu.java.scrapper.data.db.entity.TelegramChat;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelegramChatDao extends JpaRepository<TelegramChat, Long> {
    @Modifying(clearAutomatically = true)
    @Query(
            value = "INSERT INTO telegram_chat (id, registered_at) VALUES (:id, :registered_at)",
            nativeQuery = true
    )
    int create(@Param("id") Long id, @Param("registered_at") OffsetDateTime registeredAt);

    @Modifying(clearAutomatically = true)
    @Query(
            value = "DELETE from telegram_chat WHERE id=:id AND registered_at=:registered_at",
            nativeQuery = true
    )
    int delete(@Param("id") Long id, @Param("registered_at") OffsetDateTime registeredAt);

    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE telegram_chat SET registered_at=:registered_at WHERE id=:id",
            nativeQuery = true
    )
    int update(@Param("id") Long id, @Param("registered_at") OffsetDateTime registeredAt);
}
