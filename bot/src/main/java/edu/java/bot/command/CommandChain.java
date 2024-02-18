package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.MessagesUtils.ONLY_TEXT_TO_SEND;

@Component
@Log4j2
@RequiredArgsConstructor
public class CommandChain {

    private final CommandExecutor commandExecutor;

    public SendMessage executeCommand(String command, long chatId) {
        if (command == null) {
            log.info("Null message has been received");
            return new SendMessage(chatId, ONLY_TEXT_TO_SEND);
        }
        return commandExecutor.execute(command, chatId);
    }
}
