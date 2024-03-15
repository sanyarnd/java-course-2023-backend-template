package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.DeleteMyCommands;
import com.pengrad.telegrambot.request.GetMyCommands;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMyCommandsResponse;
import edu.java.bot.bot.messageProcessor.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NireblTinkoffCourseBot implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor messageProcessor;
    private final int botOwner;

    @Autowired
    public NireblTinkoffCourseBot(
        UserMessageProcessor messageProcessor,
        NireblTinkoffCourseBotProperties properties
    ) {
        this.messageProcessor = messageProcessor;
        bot = new TelegramBot(properties.getToken());
        this.botOwner = properties.getBotOwner();
    }

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(updates -> {
            // ... process updates
            // return id of last processed update or confirm them all
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } //else {
                // probably network error
//                e.printStackTrace();
//            }
        });
        this.start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            try {
                bot.execute(messageProcessor.process(update));
            } catch (Exception e) {
//                System.out.println(e.getMessage());
            }
        }

        return 0;
    }

    @Override
    public void start() {
        // get available commands
        var availableCommands = new ArrayList<BotCommand>();
        for (var command : messageProcessor.commands()) {
            availableCommands.add(command.toApiCommand());
        }

        // get current commands menu in bot
        GetMyCommandsResponse currentCommands = bot.execute(new GetMyCommands());
        // check if current menu is equal to available commands
        if (!Arrays.equals(currentCommands.commands(), availableCommands.toArray())) {
            // if not, delete current menu in bot
            DeleteMyCommands clearCommands = new DeleteMyCommands();
            clearCommands.scope(new BotCommandScopeDefault());
            bot.execute(clearCommands);

            // create new menu with available commands
            SetMyCommands cmd = new SetMyCommands(availableCommands.toArray(BotCommand[]::new));
            cmd.scope(new BotCommandScopeDefault());
            bot.execute(cmd);
        }
        bot.execute(new SendMessage(this.botOwner, "bot successfully launched"));
    }

    @Override
    public void close() {
        bot.execute(new SendMessage(this.botOwner, "bot stopped"));
    }
}
