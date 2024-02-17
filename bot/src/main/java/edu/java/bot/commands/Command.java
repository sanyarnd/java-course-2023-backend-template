package edu.java.bot.commands;

import edu.java.bot.exceptions.UserIsNotRegisteredException;

public interface Command {
    String run(long chatId) throws UserIsNotRegisteredException;

    String handleNext(long chatId, String message) throws UserIsNotRegisteredException;
}
