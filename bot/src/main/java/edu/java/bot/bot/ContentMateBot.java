package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.ICommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processors.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ContentMateBot implements IBot {

    private final TelegramBot telegramBot;
    private final UserProcessor userProcessor;

    @Autowired
    public ContentMateBot(ApplicationConfig applicationConfig, UserProcessor userProcessor) {
        this.telegramBot = new TelegramBot(applicationConfig.telegramToken());
        this.userProcessor = userProcessor;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public void start() {
        initMenu();
        telegramBot.setUpdatesListener(this);
    }

    private void initMenu() {
        telegramBot.execute(
            new SetMyCommands(
                userProcessor.commands()
                    .stream()
                    .map(ICommand::toApiCommand)
                    .toArray(BotCommand[]::new)
            )
        );
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            if (!update.message().from().username().equals("ContentMate2_Bot")) {
                if (update.message() != null && update.message().text() != null) {
                    var messageToUser = userProcessor.process(update);
                    telegramBot.execute(messageToUser);
                }
            }
        }

        return 0;
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
        telegramBot.shutdown();
    }
}
