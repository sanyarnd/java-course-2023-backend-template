package edu.java.bot.command;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.MessagesUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HelpCommand extends CommandExecutor {

    @Override
    protected SendMessage execute(String command, long chatId) {
        if (!isHelpCommand(command)) {
            return executeNext(command, chatId);
        }
        log.info("Command /help has been executed");
        return createHelpResponse(chatId);
    }

    private boolean isHelpCommand(String command) {
        return command.equals(Command.HELP.getCommandName());
    }

    private SendMessage createHelpResponse(long chatId) {
        return new SendMessage(chatId, MessagesUtils.HELP_MESSAGE)
            .parseMode(ParseMode.HTML);
    }
}
