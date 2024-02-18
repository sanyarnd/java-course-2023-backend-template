package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;

public interface ICommand {
    boolean isWaiting();

    String getName();

    boolean processCommand(Bot bot, Update update);
}
