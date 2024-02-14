package edu.java.bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.Bot;
import edu.java.bot.service.UserMessageProcessor;
import edu.java.bot.util.BotCommandSetter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotImpl implements Bot {

    private final TelegramBot telegramBot;
    private final UserMessageProcessor messageProcessor;

    public BotImpl(
        ApplicationConfig config,
        UserMessageProcessor messageProcessor,
        BotCommandSetter botCommandSetter
    ) {
        this.telegramBot = new TelegramBot(config.telegramToken());
        this.messageProcessor = messageProcessor;
        botCommandSetter.setCommands();
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage response = messageProcessor.process(update);
            if (response != null) {
                execute(response);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }
}
