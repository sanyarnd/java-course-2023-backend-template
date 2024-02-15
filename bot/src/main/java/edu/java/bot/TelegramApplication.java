package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

public class TelegramApplication {
    public static BotState state;
    public static void run(String token) {
        TelegramBot bot = new TelegramBot("6870999535:AAETyZO2CVEhx-_mk30oFO7W03Tv5maNrK4");

        bot.setUpdatesListener(updates -> {
            System.out.println("hui");
            for (Update update : updates) {
                System.out.println("mem");

            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
// Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });
    }

}
