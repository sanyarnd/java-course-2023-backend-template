package edu.java.bot.command;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.log4j.Log4j2;
import static edu.java.bot.command.Command.HELP;
import static edu.java.bot.util.MessagesUtils.HELP_MESSAGE;

@Log4j2
public class HelpCommand extends CommandExecutor {

    @Override
    protected SendMessage execute(String command, long chatId) {
        if (!command.equals(HELP.getCommandName())) {
            return executeNext(command, chatId);
        }
        log.info("Command /help has executed");
        return new SendMessage(chatId, HELP_MESSAGE).parseMode(ParseMode.HTML);
    }
}
