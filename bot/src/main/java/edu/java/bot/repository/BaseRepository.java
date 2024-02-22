package edu.java.bot.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.domain.Chat;
import edu.java.bot.exception.NoSuchChatException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository {
    private final Gson gson = new Gson();
    private final String databasePath;
    protected static final String ERROR_MESSAGE = "Unable to load database.";

    public BaseRepository(ApplicationConfig applicationConfig) {
        this.databasePath = applicationConfig.localDbPath();
    }

    protected List<Chat> readDatabase() throws IOException {
        try (FileReader reader = new FileReader(databasePath, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<ArrayList<Chat>>(){}.getType();
            List<Chat> chats = gson.fromJson(reader, listType);
            return chats != null ? chats : new ArrayList<>();
        }
    }

    protected void writeDatabase(List<Chat> chats) throws IOException {
        try (FileWriter writer = new FileWriter(databasePath, StandardCharsets.UTF_8)) {
            gson.toJson(chats, writer);
        }
    }

    protected Chat findChat(List<Chat> chats, long chatId) {
        Optional<Chat> optionalChat = chats.stream().filter(c -> c.getId() == chatId).findFirst();
        if (optionalChat.isEmpty()) {
            throw new NoSuchChatException("Cannot find chat " + chatId);
        }
        return optionalChat.get();
    }
}
