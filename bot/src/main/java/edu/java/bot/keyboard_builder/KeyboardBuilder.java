package edu.java.bot.keyboard_builder;


import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import edu.java.bot.dto.Link;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KeyboardBuilder {

    public static Keyboard createUrlKeyboard(List<Link> links) {
        return new InlineKeyboardMarkup(
            links.stream()
                .map(link -> new InlineKeyboardButton(link.link()).url(link.link()))
                .map(button -> new InlineKeyboardButton[] {button})
                .toArray(InlineKeyboardButton[][]::new)
        );
    }

    public static Keyboard createCallbackKeyboard(List<Link> links) {
        return new InlineKeyboardMarkup(
            links.stream()
                .map(link -> new InlineKeyboardButton(link.link()).callbackData("/untrack:" + link.linkId()))
                .map(button -> new InlineKeyboardButton[] {button})
                .toArray(InlineKeyboardButton[][]::new)
        );
    }
}
