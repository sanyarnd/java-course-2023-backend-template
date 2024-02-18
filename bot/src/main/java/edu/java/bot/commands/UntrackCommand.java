package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.MapStorage;
import java.net.URI;

public class UntrackCommand implements Command {
    private final MapStorage mapStorage;
    private static final String SUCCESSFULLY_UNSUBSCRIBED = "you have successfully unsubscribed from the resource";
    private static final String DONT_SUBSCRIBED = "You don't subscribe to this resource";
    private static final String USER_ERROR = "you aren't logged in, type /start";

    public UntrackCommand(MapStorage mapStorage) {
        this.mapStorage = mapStorage;
    }


    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "stop tracking the link";
    }

    @Override
    public SendMessage handle(Update update) {
        String[] cmdAndUrl = update.message().text().split(" ");

        if (supports(update) && cmdAndUrl.length == 2) {
            String enteredUrl = cmdAndUrl[1];
            String scheme = "https://";
            String uri;

            if (!enteredUrl.startsWith(scheme)) {
                uri = scheme + enteredUrl;
            } else {
                uri = enteredUrl;
            }

            URI link = URI.create(uri);

            try {
                if (!mapStorage.isSubExists(update, link)) {
                    mapStorage.deleteSubscription(update, link);
                    return new SendMessage(
                        update.message().chat().id(),
                        SUCCESSFULLY_UNSUBSCRIBED
                    );
                } else {
                    return new SendMessage(
                        update.message().chat().id(),
                        DONT_SUBSCRIBED
                    );
                }
            } catch (Exception e) {
                return new SendMessage(
                    update.message().chat().id(),
                    USER_ERROR
                );
            }
        }
        return new SendMessage(
            update.message().chat().id(),
            "type /untrack + URL of the resource you want to unsubscribe from"
        );
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().split(" ")[0].equals(command());
    }
}
