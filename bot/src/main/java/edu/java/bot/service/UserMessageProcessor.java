package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.Command;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
