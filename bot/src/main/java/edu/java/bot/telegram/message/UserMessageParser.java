package edu.java.bot.telegram.message;

import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class UserMessageParser {
    public String[] getMessageArgs(String message) {
        String[] messageParts = message.split("\s+");
        if (messageParts.length > 1) {
            return Arrays.copyOfRange(messageParts, 1, messageParts.length);
        }
        return null;
    }
}
