package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.CommandEnum;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResponse {
    protected final Update update;
    protected final long chatId;

    @SuppressWarnings("HiddenField")
    public AbstractResponse(Update update) {
        long chatId = 0;
        this.update = update;

        // Необходимо получить id чата, но мы не знаем, какой объект вернулся
        if (update.message() != null) {
            chatId = update.message().chat().id();
        }

        if (update.editedMessage() != null) {
            chatId = update.editedMessage().chat().id();
        }

        if (update.inlineQuery() != null) {
            chatId = update.inlineQuery().from().id();
        }

        if (update.chosenInlineResult() != null) {
            chatId = update.chosenInlineResult().from().id();
        }

        if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().from().id();
        }

        this.chatId = chatId;
    }

    abstract public ResponseData makeResponse();

    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.START.getCommand(), CommandEnum.START.getDescription()));
        commands.add(new BotCommand(CommandEnum.HELP.getCommand(), CommandEnum.HELP.getDescription()));
        commands.add(new BotCommand(CommandEnum.TRACK.getCommand(), CommandEnum.TRACK.getDescription()));
        commands.add(new BotCommand(CommandEnum.UNTRACK.getCommand(), CommandEnum.UNTRACK.getDescription()));
        commands.add(new BotCommand(CommandEnum.LIST.getCommand(), CommandEnum.LIST.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }

    protected SendMessage createMessageRequest(String message) {
        return new SendMessage(chatId, message)
                .disableWebPagePreview(true)
                .parseMode(ParseMode.Markdown);
    }
}
