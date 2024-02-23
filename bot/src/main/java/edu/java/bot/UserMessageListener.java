package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class UserMessageListener implements UserMessageProcessor {
    private static ApplicationContext context;
    private final CommandRecognizer recognizer;
    private final LinkValidator validator;
    private boolean dialogState = false;

    @Autowired
    public UserMessageListener(CommandRecognizer recognizer, LinkValidator validator) {
        this.recognizer = recognizer;
        this.validator = validator;
    }

    @Override
    public SendMessage process(Update update) {
        return dialogState ? linkValidationInDialog(update) : recognizeCommand(update);
    }

    public SendMessage recognizeCommand(Update update) {
        Command command = recognizer.recognize(update);
        if (command.getClass().equals(TrackCommand.class)) {
            dialogState = true;
        }
        return command.handle(update);
    }

    public SendMessage linkValidationInDialog(Update update) {
        String link = update.message().text();
        String messageText = validator.validate(link);
        if (validator.isLinkCorrect(link)) {
            // ADD LINK TO DB
            dialogState = false;
        }
        return new SendMessage(update.message().chat().id(), messageText);
    }
}
