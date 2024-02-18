package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;
import edu.java.bot.data.UsersTracks;
import org.springframework.beans.factory.annotation.Autowired;

public interface ICommand {
    String getName();

    boolean processCommand(Bot bot, Update update);
}
