package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.SyntheticLinkRepository;
import edu.java.bot.util.Links;
import java.util.Objects;

public class ListCommand implements ICommand {

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder sb = new StringBuilder();
        var chatId = update.message().chat().id();
        var linkMap = Links.splitByDomain(SyntheticLinkRepository.getAllByChatId(chatId));
        if (Objects.isNull(linkMap)) {
            return new SendMessage(chatId, "Ваш список отслеживаемых сайтов пуст :(\nДавайте это исправим! **Напишите команду** /track");
        }
        var domains = linkMap.keySet();
        for (var domain : domains) {
            sb.append("**").append(domain).append("**\n");
            for (var link : linkMap.get(domain)) {
                sb.append("  --").append(link).append("\n");
            }
        }
        return new SendMessage(chatId, sb.toString());
    }
}
