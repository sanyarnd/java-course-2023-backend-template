package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.ResponseData;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActionFacade {
    private final Action chainHead;

    public ActionFacade(List<Action> actions) {
        this.chainHead = ChainElement.buildChain(actions, new UnknownCommandAction());
    }

    public ResponseData apply(Update update) {
        return chainHead.apply(update);
    }
}
