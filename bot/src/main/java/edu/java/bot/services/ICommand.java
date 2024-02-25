package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;

public interface ICommand {

    String getName();

    String getDescription();

    String processCommand(Update update);
}
