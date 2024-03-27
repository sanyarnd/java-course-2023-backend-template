package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface IMultiLineCommand extends ICommand {

    SendMessage userResponseHandler(Update update);

}
