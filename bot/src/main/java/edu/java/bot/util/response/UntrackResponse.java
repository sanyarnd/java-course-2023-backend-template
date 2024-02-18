package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.CommandEnum;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class UntrackResponse extends AbstractResponse {
    // TODO Заменить на сущности url + id (скорее всего модели)
    private final List<String> urls;

    /**
     * Указатель для cursor-пагинации
     */
    private final String cursor;

    @Override
    public ResponseData makeResponse() {
        if (this.urls.isEmpty()) {
            var response = new SendMessage(update.message().chat().id(), "Список ссылок пуст.")
                    .disableWebPagePreview(true);

            return new ResponseData(
                    response,
                    super.getCommands()
            );
        }

        String message = "Выберите, для какой ссылки прекратить отслеживание.\n"
                + "Для отмены выбора используйте " + CommandEnum.CANCEL.getCommand() + ".";

        var keyboard = new InlineKeyboardMarkup();
        for (String url : this.urls) {
            keyboard.addRow(new InlineKeyboardButton(url).callbackData(url));
        }

        if (cursor != null) {
            // TODO Реализовать удаление предыдущего сообщения при выборе кнопки
            keyboard.addRow(new InlineKeyboardButton("Показать еще").callbackData(cursor));
        }

        return new ResponseData(createMessageRequest(message).replyMarkup(keyboard), getCommands());
    }

    @Override
    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.CANCEL.getCommand(), CommandEnum.CANCEL.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }

    public UntrackResponse(Update update, List<String> urls, @NotNull String cursor) {
        super(update);

        this.urls = urls;
        this.cursor = cursor;
    }

    public UntrackResponse(Update update, List<String> urls) {
        super(update);

        this.urls = urls;
        this.cursor = null;
    }
}
