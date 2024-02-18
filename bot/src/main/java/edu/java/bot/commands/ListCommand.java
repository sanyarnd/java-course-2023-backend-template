package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.MapStorage;
import java.net.URI;
import java.util.List;

public class ListCommand implements Command {

    private final MapStorage mapStorage;
    private static final String WITHOUT_SUBS = "You aren't subscribed to anything";
    private static final String USER_ERROR = "you aren't logged in, type /start";
    private static final String WRONG_COMMAND = "The command was entered incorrectly! Type /list to view your subscriptions";

    public ListCommand(MapStorage mapStorage) {
        this.mapStorage = mapStorage;
    }


    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "show a list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        if (supports(update)) {
            try {
                List<URI> links = mapStorage.getSubscriptions(update);

                if (links.isEmpty()) {
                    return new SendMessage(
                        update.message().chat().id(), WITHOUT_SUBS
                    );
                }

                StringBuilder strLinks = new StringBuilder("your subscriptions:\n");

                for (var link : links) {
                    strLinks.append(link.toString()).append("\n");
                }
                return new SendMessage(
                    update.message().chat().id(),
                    strLinks.toString()
                );



            } catch (Exception e){
                return new SendMessage(
                    update.message().chat().id(),
                    USER_ERROR
                );
            }
        }
        return new SendMessage(
            update.message().chat().id(),
            WRONG_COMMAND
        );
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().equals(command());
    }

}
