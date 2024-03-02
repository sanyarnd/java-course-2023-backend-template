package edu.java.bot.view.telegram;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class BotExceptionHandler implements ExceptionHandler {
    @Override
    public void onException(TelegramException e) {
        log.warn(Arrays.toString(e.getStackTrace()));
    }
}
