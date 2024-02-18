package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.CommandEnum;
import java.util.ArrayList;
import java.util.List;

public class TrackResponse extends AbstractResponse {
    public TrackResponse(Update update) {
        super(update);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Введите ссылку для отслеживания.\n"
                + "Помните, что бот на данный момент умеет отслеживать только GitHub и StackOverFlow.";

        return new ResponseData(createMessageRequest(message), getCommands());
    }

    @Override
    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.CANCEL.getCommand(), CommandEnum.CANCEL.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }
}
