package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ListResponse extends AbstractResponse {
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

        String message = "Список отслеживаемых страниц.";

        var keyboard = new InlineKeyboardMarkup();
        for (String url : this.urls) {
            keyboard.addRow(new InlineKeyboardButton(url));
        }

        if (cursor != null) {
            keyboard.addRow(new InlineKeyboardButton("Показать еще").callbackData(cursor));
        }

        return new ResponseData(createMessageRequest(message).replyMarkup(keyboard), getCommands());
    }

    public ListResponse(Update update, List<String> urls, @NotNull String cursor) {
        super(update);

        this.urls = urls;
        this.cursor = cursor;
    }

    public ListResponse(Update update, List<String> urls) {
        super(update);

        this.urls = urls;
        this.cursor = null;
    }
}
