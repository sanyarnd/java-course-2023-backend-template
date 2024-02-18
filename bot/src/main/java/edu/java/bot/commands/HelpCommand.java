package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand implements Command {

    private final List<Command> commandList;

    private static final String WRONG_COMMAND = "The command was entered incorrectly! Type /help to view the available commands";

    public HelpCommand(List<Command> commandList) {
        this.commandList = commandList;
    }


    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "provides a list of available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        if (supports(update)) {
            StringBuilder answer = new StringBuilder("Available commands:\n");

            answer.append(commandList.stream()
                .map(
                    c -> c.command() + " - " + c.description() + "\n"
                ).collect(Collectors.joining()));

            return new SendMessage(
                update.message().chat().id(),
                answer.toString()
            );
        }
        return new SendMessage(update.message().chat().id(), WRONG_COMMAND);
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().equals(command());
    }
}
