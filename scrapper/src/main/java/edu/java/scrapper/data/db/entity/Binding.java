package edu.java.scrapper.data.db.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "chat_to_link_binding")
@IdClass(Binding.BindingPK.class)
public class Binding implements Serializable {
    @Id
    private Long chatId;

    @Id
    private Long linkId;

    public Binding(TelegramChat chat, Link link) {
        this.chatId = chat.getId();
        this.linkId = link.getId();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class BindingPK implements Serializable {
        @Column(name = "chat_id", nullable = false)
        private Long chatId;

        @Id
        @Column(name = "link_id", nullable = false)
        private Long linkId;
    }
}
