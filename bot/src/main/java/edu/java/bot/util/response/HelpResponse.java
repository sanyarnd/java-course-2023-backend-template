package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.CommandEnum;

public class HelpResponse extends AbstractResponse {
    public HelpResponse(Update update) {
        super(update);
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "*Список команд:*\n\n"
                + CommandEnum.TRACK.getCommand() + " - " + CommandEnum.TRACK.getDescription() + "\n"
                + CommandEnum.UNTRACK.getCommand() + " - " + CommandEnum.UNTRACK.getDescription() + "\n"
                + CommandEnum.LIST.getCommand() + " - " + CommandEnum.LIST.getDescription() + "\n";

        return new ResponseData(createMessageRequest(message), getCommands());
    }
}
