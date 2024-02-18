package edu.java.bot.message_sender;


import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    SendResponse sendMessage(SendMessage message);
}
