package edu.java.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscribesDTO {
    Long chatId;
    Long linkId;
}
