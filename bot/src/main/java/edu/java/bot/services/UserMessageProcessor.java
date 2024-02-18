package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
