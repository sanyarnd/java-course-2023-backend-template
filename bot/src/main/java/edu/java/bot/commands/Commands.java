package edu.java.bot.commands;

import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter @Component
public class Commands {
    final List<AbstractCommand> list;

    public Commands(List<AbstractCommand> abstractCommandList) {
        this.list = abstractCommandList;
    }

    public AbstractCommand[] asArray() {
        return list.toArray(new AbstractCommand[0]);
    }
}
