package edu.java.bot.commands;

public abstract class CommandWithArgs extends AbstractCommand {
    protected CommandWithArgs(String command, String description, String answer) {
        super(command, description, answer, true);
    }

    @Override public String run(long chatId) {
        states.setState(chatId, this);
        return answer;
    }
}
