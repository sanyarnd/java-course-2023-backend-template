package edu.java.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChatsDTO {
    Long telegramId;


    public ChatsDTO(Long telegramId) {
        this.telegramId = telegramId;
    }
}
