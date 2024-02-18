package edu.java.bot.command;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.Link;
import edu.java.bot.keyboard_builder.KeyboardBuilder;
import edu.java.bot.service.CommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static edu.java.bot.command.Command.UNTRACK;
import static edu.java.bot.util.MessagesUtils.CHOOSE_LINK_TO_UNTRACK;
import static edu.java.bot.util.MessagesUtils.NO_TRACKED_LINKS;

@Log4j2
@RequiredArgsConstructor
public class UntrackCommand extends CommandExecutor {

    private final CommandService linkService;

    @Override
    protected SendMessage execute(String command, long chatId) {
        if (!command.equals(UNTRACK.getCommandName())) {
            return executeNext(command, chatId);
        }
        log.info("Command /untrack has executed");
        return buildMessage(chatId);
    }

    private SendMessage buildMessage(long chatId) {
        List<Link> links = linkService.getAllTrackedLinks(chatId);
        if (links.isEmpty()) {
            return new SendMessage(chatId, NO_TRACKED_LINKS);
        }
        Keyboard keyboard = KeyboardBuilder.createCallbackKeyboard(links);
        return new SendMessage(chatId, CHOOSE_LINK_TO_UNTRACK).replyMarkup(keyboard);
    }
}
