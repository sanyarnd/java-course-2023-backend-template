package edu.java.bot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LinkTrackerBot extends TelegramBot {

    private final BotCommand[] commands;
    private final UpdatesListener updatesListener;

    public LinkTrackerBot(
        String telegramToken, BotCommand[] commands,
        @Lazy UpdatesListener updatesListener
    ) {
        super(telegramToken);
        this.commands = commands;
        this.updatesListener = updatesListener;
    }

    @PostConstruct
    private void start() {
        log.info("Bot has been started");
        execute(new SetMyCommands(commands));
        setUpdatesListener(updatesListener);
    }
}
