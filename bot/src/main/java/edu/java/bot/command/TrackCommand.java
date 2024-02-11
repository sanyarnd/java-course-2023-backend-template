package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.LinkParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.TRACK;
    private final LinkParser linkParser;

    @Override
    public SendMessage processCommand(Update update) {
        // TODO
        String[] messageArgs = linkParser.getMessageArgs(update.message().text());
        return new SendMessage(update.message().chat().id(), "test message command /track");
    }

    @Override
    public String type() {
        return commandInfo.getType();
    }

    @Override
    public String description() {
        return commandInfo.getDescription();
    }
}
