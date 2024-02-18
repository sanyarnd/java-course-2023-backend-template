package edu.java.bot.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.bot.messageProcessor.UserMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NireblTinkoffCourseBot implements Bot {
    private final TelegramBot bot;

    @Autowired
    private UserMessageProcessor messageProcessor;

    public NireblTinkoffCourseBot(@Value("${app.telegram-token}") String botToken) {
        bot = new TelegramBot(botToken);
//        bot.setUpdatesListener();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            bot.execute(messageProcessor.process(update));
        }

        return 0;
    }

    @Override
    public void start() {
        bot.execute(new SendMessage("594363938", "hello"));

    }

    @Override
    public void close() {
        bot.execute(new SendMessage("594363938", "goodbye"));
    }
}
