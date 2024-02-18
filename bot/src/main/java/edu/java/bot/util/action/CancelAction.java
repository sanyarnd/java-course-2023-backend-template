package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.StartResponse;
import java.util.Optional;

public class CancelAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.CANCEL.getCommand())) {
            return Optional.empty();
        }

        // TODO Пока что возвращает стартовый ответ
        // TODO Реализовать удаление предыдущего сообщения при вводе команды

        return Optional.of(new StartResponse(update).makeResponse());
    }
}
