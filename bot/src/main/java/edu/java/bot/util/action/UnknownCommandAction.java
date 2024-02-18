package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.UnknownCommandResponse;
import java.util.Optional;

public class UnknownCommandAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        return Optional.of(new UnknownCommandResponse(update).makeResponse());
    }
}
