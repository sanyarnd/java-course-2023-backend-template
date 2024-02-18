package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.SyntheticLinkRepository;
import edu.java.bot.util.UserResponseValidators;

public class UntrackCommand implements ICommand {

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var links = SyntheticLinkRepository.getAllByChatId(chatId);
        StringBuilder sb = new StringBuilder("Введите номер ссылки, которую хотите удалить:\n");
        for (int i = 0; i < links.size(); i++) {
            sb.append(i).append(". '").append(links.get(i)).append("'\n");
        }
        return new SendMessage(chatId, sb.toString());
    }

    @Override
    public SendMessage userResponseHandler(Update update) {
        var chatId = update.message().chat().id();
        var userResponse = update.message().text();
        var links = SyntheticLinkRepository.getAllByChatId(chatId);

        if (UserResponseValidators.listIndexValidate(userResponse, links)) {
            var i = Integer.parseInt(userResponse);
            SyntheticLinkRepository.delete(chatId, i);
            return new SendMessage(chatId, "Теперь вы не отслеживаете '" + links.get(i) + "'");
        }

        return new SendMessage(chatId, "Введите номар записи, которую хотите удалить\nНапример:\n1");
    }
}
