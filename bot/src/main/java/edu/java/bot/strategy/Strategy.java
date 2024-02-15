package edu.java.bot.strategy;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.exceptions.ResolvingException;

public interface Strategy {

    String resolve(Update update) throws ResolvingException;

}
