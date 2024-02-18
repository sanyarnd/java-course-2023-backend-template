package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrackingBot implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;

    @Autowired
    public TrackingBot(@Value("${bot.token}") String botToken, UserMessageProcessor userMessageProcessor) {
        bot = new TelegramBot(botToken);
        this.userMessageProcessor = userMessageProcessor;
    }

    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            var message = userMessageProcessor.process(update);
            if (message != null) {
                execute(message);
            }
            execute(new SetMyCommands(userMessageProcessor.commands().stream().map(Command::toApiCommand)
                .toArray(BotCommand[]::new)));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

}
