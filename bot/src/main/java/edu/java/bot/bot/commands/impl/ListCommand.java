package edu.java.bot.bot.commands.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.commands.Command;
import edu.java.bot.scrappers.Scrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ListCommand implements Command {
//    @Autowired
//    private Scrapper scrapper;

//    public ListCommand(Scrapper scrapper)
//    {
//        this.scrapper=scrapper;
//    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "get your tracking urls list";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "list");
    }
}
