package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.CommandEnum;

public class StartResponse extends AbstractResponse {

    public StartResponse(Update update) {
        super(update);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Привет! Этот Бот создан для удобного получения оповещений об изменении"
                + " на различных веб-ресурсах: GitHub, StackOverFlow, Reddit, HackerNews, YouTube и т.п.\n\n"
                + "На данный момент поддерживается только GitHub и StackOverFlow.\n\n"
                + CommandEnum.HELP.getCommand() + " - вывести список команд";

        return new ResponseData(createMessageRequest(message), getCommands());
    }
}
