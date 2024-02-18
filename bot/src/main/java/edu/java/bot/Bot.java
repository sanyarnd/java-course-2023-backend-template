package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bot implements UpdatesListener, AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CommandRecognizer recognizer = new CommandRecognizer();
    private final LinkValidator validator = new LinkValidator();
    private final TelegramBot bot;
    private boolean dialogState = false;

    public Bot(String token) {
        this.bot = new TelegramBot(token);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            bot.execute(generateResponse(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private SendMessage generateResponse(Update update) {
        return new SendMessage(update.message().chat().id(),
            dialogState ? linkValidationInDialog(update) : recognizeCommand(update));
    }

    public String recognizeCommand(Update update) {
        Command command = recognizer.recognize(update);
        if (command.getClass().equals(TrackCommand.class)) {
            dialogState = true;
        }
        return command.command();
    }

    public String linkValidationInDialog(Update update) {
        String link = update.message().text();
        String messageText = validator.validate(link);
        if (validator.isLinkCorrect(link)) {
            TrackCommand linkAdder = new TrackCommand();
            linkAdder.addLink(link);
            dialogState = false;
        }
        return messageText;
    }

    public void start() {
        bot.setUpdatesListener(
            this::process,
            e -> {
                if (e.response() != null) {
                    // got bad response from telegram
                    e.response().errorCode();
                    e.response().description();
                } else {
                    LOGGER.warn("probably network error");
                }
            }
        );
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
