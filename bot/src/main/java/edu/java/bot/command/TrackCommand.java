package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final static String REPLY_TEXT = "Send the link that you want to track!";

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "starts tracking link";
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().trim().equals(command()) || isReply(update);
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String link = update.message().text().trim();

        if (isReply(update)) {
            return new SendMessage(chatId, "Now we are tracking " + link);

        }
        return new SendMessage(chatId, REPLY_TEXT).replyMarkup(new ForceReply());

    }

    private boolean isReply(Update update) {
        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(REPLY_TEXT);
    }
}
