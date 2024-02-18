package edu.java.bot.model;

import java.util.Optional;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.Value;

public record TelegramAnswer(Optional<String> text) {
}
