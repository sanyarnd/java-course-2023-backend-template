package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.SyntheticLinkRepository;
import edu.java.bot.util.Links;

public class TrackCommand implements IMultiLineCommand {

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(),
            "Отправьте ссылку на ресурс, который желаете отслеживать:");
    }

    @Override
    public SendMessage userResponseHandler(Update update) {
        var chatId = update.message().chat().id();
        var link = update.message().text();
        if (Links.isValid(link)) {
            SyntheticLinkRepository.add(chatId, link);
            return new SendMessage(chatId, "'" + link + "' - добавлена в список отслеживаемых сайтов!");
        }
        return new SendMessage(chatId, "Я не умею отслеживать обноления по ссылке '" + link + "'");
    }
}
