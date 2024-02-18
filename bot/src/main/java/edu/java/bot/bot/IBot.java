package edu.java.bot.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

public interface IBot extends AutoCloseable, UpdatesListener {

    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    void start();
}
