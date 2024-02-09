package edu.java.bot.telegram.bot;

import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface Bot {
    int processUpdates(List<Update> updates);

    void processCurrentUpdate(Update update);
}
