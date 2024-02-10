package edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.telegram.message.UserMessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.TRACK;
    private final UserMessageParser userMessageParser;

    @Autowired
    public TrackCommand(UserMessageParser userMessageParser) {
        this.userMessageParser = userMessageParser;
    }

    @Override
    public SendMessage processCommand(Update update) {
        // TODO
        String[] messageArgs = userMessageParser.getMessageArgs(update.message().text());
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
