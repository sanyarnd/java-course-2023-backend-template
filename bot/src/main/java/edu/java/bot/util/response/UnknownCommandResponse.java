package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;

public class UnknownCommandResponse extends AbstractResponse {
    public UnknownCommandResponse(Update update) {
        super(update);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Неизвестная команда";

        return new ResponseData(createMessageRequest(message), getCommands());
    }
}
