package edu.java.bot.dto;

import java.util.List;

public record LinkUpdate(
    int id,
    String url,
    String description,
    List<Integer> telegramChatIds

) {
}
