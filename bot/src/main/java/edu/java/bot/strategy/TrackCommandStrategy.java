package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public class TrackCommandStrategy implements Strategy{

    static TrackCommandStrategy instance;

    private TrackCommandStrategy(){}

    public static Strategy getInstance() {
        if (instance == null) instance = new TrackCommandStrategy();
        return instance;
    }

    @Override
    public String resolve(Update update) throws ResolvingException {
        return null;
    }
}
