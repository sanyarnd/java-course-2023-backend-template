package edu.java.bot.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessor;
import org.springframework.stereotype.Component;

@Component
public class BotCommandSetter {
    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;

    public BotCommandSetter(ApplicationConfig applicationConfig, UserMessageProcessor userMessageProcessor) {
        this.bot = new TelegramBot(applicationConfig.telegramToken());
        this.userMessageProcessor = userMessageProcessor;
    }

    public void setCommands() {
        BotCommand[] botCommandList = userMessageProcessor.commands().stream()
            .map(cm -> new BotCommand(cm.command(), cm.description()))
            .toArray(BotCommand[]::new);

        bot.execute(new SetMyCommands(botCommandList));
    }
}
