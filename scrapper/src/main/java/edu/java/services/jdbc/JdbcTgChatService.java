package edu.java.services.jdbc;

import edu.java.controller.exception.ChatNotFoundException;
import edu.java.controller.exception.ChatReAddingException;
import edu.java.domain.JdbcChatsDAO;
import edu.java.services.interfaces.TgChatService;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {
    JdbcChatsDAO chatRepository;

    public JdbcTgChatService(JdbcChatsDAO chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void addUser(Long chatId) {
        if (chatRepository.contains(chatId)) {
            throw new ChatReAddingException("Пользователь со следующим id уже добавлен: " + chatId);
        }
        chatRepository.add(chatId);
    }

    @Override
    public void remove(Long chatId) {
        if (!chatRepository.contains(chatId)) {
            throw new ChatNotFoundException("Нет чата с id: " + chatId);
        }
        chatRepository.remove(chatId);
    }
}
