package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;

public class TrackUrlResponse extends AbstractResponse {

    private final String url;

    public TrackUrlResponse(Update update, String url) {
        super(update);

        this.url = url;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Ссылка " + url + " успешно добавлена для отслеживания.";

        return new ResponseData(createMessageRequest(message), getCommands());
    }
}
