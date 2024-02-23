package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BotUpdateListener implements UserMessageProcessor, ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public List<? extends Command> commands() {
        var map = context.getBeansOfType(Command.class);
        return map.values().stream().toList();
    }

    @Override
    public SendMessage process(Update update) {
        return null;
    }
}
