package edu.java.bot.bot.commands.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;
import edu.java.bot.scrappers.Scrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TrackCommand implements Command {
//    @Autowired
//    private Scrapper scrapper;

//    @Autowired
//    public TrackCommand(Scrapper scrapper) {
//        this.scrapper = scrapper;
//    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "track some url example: '/track google.com'";
    }

    @Override
    public SendMessage handle(Update update) {
//        scrapper.track(update.message().chat().id(), update.message().text().split(" ")[1]);
        return new SendMessage(update.message().chat().id(), "tracking");

    }
}
