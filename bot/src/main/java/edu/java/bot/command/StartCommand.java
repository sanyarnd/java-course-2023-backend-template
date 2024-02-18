package edu.java.bot.command;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.log4j.Log4j2;

import static edu.java.bot.command.Command.START;
import static edu.java.bot.util.MessagesUtils.WELCOME_MESSAGE;

@Log4j2
public class StartCommand extends CommandExecutor {

    @Override
    protected SendMessage execute(String command, long chatId) {
        if (isStartCommand(command)) {
            log.info("Command /start has been executed");
            return createWelcomeMessage(chatId);
        }
        return executeNext(command, chatId);
    }

    private boolean isStartCommand(String command) {
        return START.getCommandName().equals(command);
    }

    private SendMessage createWelcomeMessage(long chatId) {
        return new SendMessage(chatId, WELCOME_MESSAGE).parseMode(ParseMode.HTML);
    }
}
