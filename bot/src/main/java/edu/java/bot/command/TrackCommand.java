package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static edu.java.bot.command.Command.TRACK;
import static edu.java.bot.util.MessagesUtils.HTTPS_PREFIX;
import static edu.java.bot.util.MessagesUtils.HTTP_PREFIX;
import static edu.java.bot.util.MessagesUtils.LINK_IS_TRACKED;
import static edu.java.bot.util.MessagesUtils.LINK_SHOULD_STARTS_WITH_HTTP;
import static edu.java.bot.util.MessagesUtils.TRACK_EXAMPLE;

@Log4j2
@RequiredArgsConstructor
public class TrackCommand extends CommandExecutor {

    private final CommandService linkService;

    @Override
    protected SendMessage execute(String command, long chatId) {
        if (!command.startsWith(TRACK.getCommandName())) {
            return executeNext(command, chatId);
        }
        log.info("Command /track has executed");
        return buildMessage(command, chatId);
    }

    private SendMessage buildMessage(String command, long chatId) {
        String[] splitCommand = command.split(" ");
        if (splitCommand.length != 2) {
            return new SendMessage(chatId, TRACK_EXAMPLE);
        }
        if (!splitCommand[1].startsWith(HTTPS_PREFIX) && !splitCommand[1].startsWith(HTTP_PREFIX)) {
            return new SendMessage(chatId, LINK_SHOULD_STARTS_WITH_HTTP);
        }
        linkService.trackLink(chatId, splitCommand[1]); // Сохраняем ссылку
        return new SendMessage(chatId, LINK_IS_TRACKED.formatted(splitCommand[1]));
    }
}
