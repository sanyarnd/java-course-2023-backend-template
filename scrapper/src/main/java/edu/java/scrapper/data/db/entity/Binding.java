package edu.java.scrapper.data.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Binding {
    private Long chatId;
    private Long linkId;

    public Binding(TelegramChat chat, Link link) {
        this.chatId = chat.getId();
        this.linkId = link.getId();
    }
}
