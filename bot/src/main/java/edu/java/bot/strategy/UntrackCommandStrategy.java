package edu.java.bot.strategy;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public class UntrackCommandStrategy implements Strategy{

    static UntrackCommandStrategy instance;

    private UntrackCommandStrategy(){}

    public static Strategy getInstance() {
        if (instance == null) instance = new UntrackCommandStrategy();
        return instance;
    }

    @Override
    public String resolve(Update update) throws ResolvingException {
        return null;
    }
}

