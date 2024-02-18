package edu.java.bot.util.response;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;

public record ResponseData(
        BaseRequest<? extends BaseRequest<?, ?>, ? extends BaseResponse> request,
        SetMyCommands menu
) {
}
