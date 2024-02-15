package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public class ListCommandStrategy implements Strategy{

    static ListCommandStrategy instance;

    private ListCommandStrategy(){}

    static public ListCommandStrategy getInstance(){
        if (instance == null) instance = new ListCommandStrategy();
        return instance;
    }

    @Override
    public String resolve(Update update) throws ResolvingException {
        return null;
    }
}
