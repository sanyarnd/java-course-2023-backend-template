package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
