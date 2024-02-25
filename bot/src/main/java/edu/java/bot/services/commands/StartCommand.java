package edu.java.bot.services.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.UsersTracks;
import edu.java.bot.services.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements ICommand {
    private UsersTracks usersTracks;
    private static final String START_MESSAGE = "Удачно использовать трэкер! Для спраки обращаться в /help\n";
    private static final String ALREADY_REGISTERED_MESSAGE = "Вы уже зарегистрированы\n";

    @Autowired StartCommand(UsersTracks usersTracks) {
        this.usersTracks = usersTracks;
    }

    @Override
    public String getName() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "Введите, чтобы начать";
    }

    @Override
    public String processCommand(Update update) {
        if (!usersTracks.addUser(update.message().chat().id())) {
            return ALREADY_REGISTERED_MESSAGE;
        }
        return START_MESSAGE;

        //TODO:: maybe, real registration??
    }
}
