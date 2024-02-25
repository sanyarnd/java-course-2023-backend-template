package edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.scrappers.Scrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UntrackCommand implements Command {
    @Autowired
    private Scrapper scrapper;

//    @Autowired
//    public UntrackCommand(Scrapper scrapper) {
//        this.scrapper = scrapper;
//    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "untrack some url example: '/untrack google.com'";
    }

    @Override
    public SendMessage handle(Update update) {
        scrapper.untrack(update.message().chat().id(), update.message().text());

        return new SendMessage(update.message().chat().id(), "untracking");

    }
}
