package edu.java.bot.view;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramException;
import edu.java.bot.util.LoggerQualifier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class BotExceptionHandler implements ExceptionHandler {
    @LoggerQualifier("TG_EXCEPTION_HANDLER")
    private final Logger logger;

    @Override
    public void onException(TelegramException e) {
        logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
    }
}
