package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.UntrackResponse;
import java.util.List;
import java.util.Optional;

public class UntrackAction  extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.UNTRACK.getCommand())) {
            return Optional.empty();
        }

        // TODO Заменить на получение ссылок из модели
        return Optional.ofNullable(new UntrackResponse(update, List.of(
                "google.com",
                "yandex.ru",
                "github.com",
                "stackoverflow.com"
        )).makeResponse());
    }
}
