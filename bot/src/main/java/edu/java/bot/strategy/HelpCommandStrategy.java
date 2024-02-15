package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public class HelpCommandStrategy implements Strategy {
    static HelpCommandStrategy instance = null;

    @Override
    public String resolve(Update update) throws ResolvingException {
        return null;
    }

    static public Strategy getInstance() {
        if (instance == null) {
            instance = new HelpCommandStrategy();
        }
        return instance;
    }

}
