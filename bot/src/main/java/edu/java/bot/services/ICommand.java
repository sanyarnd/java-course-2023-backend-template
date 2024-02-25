package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.bot.Bot;

public interface ICommand {

    String getName();

    String getDescription();

    boolean processCommand(Bot bot, Update update);
}
