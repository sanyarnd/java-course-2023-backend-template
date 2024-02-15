package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public class StartCommandStrategy implements Strategy {

    static StartCommandStrategy instance = null;

    private StartCommandStrategy() {
    }

    static public Strategy getInstance() {
        if (instance == null) {
            instance = new StartCommandStrategy();
        }
        return instance;
    }

    @Override
    public String resolve(Update update) throws ResolvingException {
        return null;
    }
}
