package edu.java.bot.repositories;

import edu.java.bot.commands.AbstractCommand;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandWithoutArgs;

public interface ChatStateRepository {

    class NoCommand extends CommandWithoutArgs {
        public static NoCommand defaultState = new NoCommand();
        private NoCommand() {
            super(null, null, null);
        }
    }

    void setState(long chatId, Command state);

    default void clearState(long chatId) {
        setState(chatId, NoCommand.defaultState);
    }
    Command getCurrentCommand(long chatId);
}
