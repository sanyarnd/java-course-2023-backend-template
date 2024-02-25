package edu.java.bot.data;

import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class UsersWaiting {
    private HashMap<Long, String> waitingForId;
    private static final String DEFAULT_WAITING = "default";

    public UsersWaiting() {
        waitingForId = new HashMap<>();
    }

    public void setWaiting(Long chatId, String command) {
        waitingForId.put(chatId, command);
    }

    public String getWaiting(Long chatId) {
        if (!waitingForId.containsKey(chatId) || waitingForId.get(chatId) == null) {
            return DEFAULT_WAITING;
        }
        return waitingForId.get(chatId);
    }

    @NotNull
    public String getDefaultWaiting() {
        return DEFAULT_WAITING;
    }
}
