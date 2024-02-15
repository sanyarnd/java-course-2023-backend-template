package edu.java.bot.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class UserChat {
    private Long chatId;
    private List<String> trackingLinks;
}
