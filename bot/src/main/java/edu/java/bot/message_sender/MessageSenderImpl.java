package edu.java.bot.message_sender;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.LinkTrackerBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log4j2
public class MessageSenderImpl implements MessageSender {

    private final LinkTrackerBot bot;

    @Override
    public SendResponse sendMessage(SendMessage message) {
        SendResponse response = bot.execute(message);
        log.info("Response is Ok: {}", response.isOk());
        log.info("Response description : {}", response.description());
        return response;
    }
}
