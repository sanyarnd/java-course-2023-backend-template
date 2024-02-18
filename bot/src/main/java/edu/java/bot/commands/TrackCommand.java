package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dao.MapStorage;
import java.net.URI;

public class TrackCommand implements Command {
    private final MapStorage mapStorage;
    private static final String SUCCESSFULLY_SUBSCRIBED = "you have successfully subscribed to the resource";
    private static final String ALREADY_SUBSCRIBED = "You have already subscribed to this resource";
    private static final String USER_ERROR = "you aren't logged in, type /start";

    public TrackCommand(MapStorage mapStorage) {
        this.mapStorage = mapStorage;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "start tracking link";
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
                    mapStorage.addSubscription(update, link);
                    return new SendMessage(
                        update.message().chat().id(),
                        SUCCESSFULLY_SUBSCRIBED
                    );
                } else {
                    return new SendMessage(
                        update.message().chat().id(),
                        ALREADY_SUBSCRIBED
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
            "type /track + url of the resource you want to subscribe to"
        );
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().split(" ")[0].equals(command());
    }

}
