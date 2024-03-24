package edu.java.scrapper.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "telegram_chat")
public class TelegramChat {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "registered_at", nullable = false)
    private OffsetDateTime registeredAt;
}
