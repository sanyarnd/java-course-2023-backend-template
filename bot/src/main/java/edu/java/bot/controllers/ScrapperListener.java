package edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class ScrapperListener {
//    private final Bot bot;
//
//    @Autowired
//    public ScrapperListener(Bot bot) {
//        this.bot = bot;
//    }
//
//    @RequestMapping("/notify")
//    public void notify(long telegram_id, String message) {
//        bot.execute(new SendMessage(telegram_id, message));
//    }
//}
