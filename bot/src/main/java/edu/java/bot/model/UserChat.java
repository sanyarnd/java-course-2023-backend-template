package edu.java.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class UserChat {
    private Long chatId;
    private List<String> trackingLinks;
}
