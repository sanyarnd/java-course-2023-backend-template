package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.ResponseData;
import java.util.Optional;

public abstract class AbstractAction implements Action {
    private Action next;

    @Override
    public final void setNext(Action next) {
        this.next = next;
    }

    @Override
    public ResponseData apply(Update update) {
        return process(update).orElseGet(() -> next.apply(update));
    }

    protected abstract Optional<ResponseData> process(Update update);
}
