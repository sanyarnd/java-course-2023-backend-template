package edu.java.bot.commands;

import edu.java.bot.exceptions.UserIsNotRegisteredException;

public abstract class CommandWithoutArgs extends AbstractCommand {
    protected CommandWithoutArgs(String command, String description, String successAnswer) {
        super(command, description, successAnswer, false);
    }

    @Override public String run(long chatId) throws UserIsNotRegisteredException {
        states.clearState(chatId);
        return answer;
    }

    @Override public String handleNext(long chatId, String message) {
        return "No selected command";
    }
}
