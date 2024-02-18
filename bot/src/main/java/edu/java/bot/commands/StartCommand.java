package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand implements ICommand {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Меня зовут ContentMate!\nМое предназначение помогать вам отслеживать обновления на ваших любимых сайтах\nНапишите /help для того чтобы узнать, какие команды доступны.");
    }
}
