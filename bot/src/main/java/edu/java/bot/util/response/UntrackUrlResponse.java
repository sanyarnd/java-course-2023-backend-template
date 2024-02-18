package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;

public class UntrackUrlResponse extends AbstractResponse {

    private final String url;

    public UntrackUrlResponse(Update update, String url) {
        super(update);

        this.url = url;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Ссылка " + url + " успешно удалена из отслеживания.";

        return new ResponseData(createMessageRequest(message), getCommands());
    }
}
