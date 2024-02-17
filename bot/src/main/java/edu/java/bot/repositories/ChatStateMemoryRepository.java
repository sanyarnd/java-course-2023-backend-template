package edu.java.bot.repositories;

import edu.java.bot.commands.Command;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class ChatStateMemoryRepository implements ChatStateRepository {
    private final ConcurrentMap<Long, Command> map = new ConcurrentHashMap<>();

    @Override
    public void setState(long chatId, Command state) {
        map.put(chatId, state);
    }

    @Override
    public Command getCurrentCommand(long chatId) {
        return map.getOrDefault(chatId, NoCommand.defaultState);
    }
}
