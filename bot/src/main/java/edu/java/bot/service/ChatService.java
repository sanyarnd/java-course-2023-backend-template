package edu.java.bot.service;

import edu.java.bot.BotState;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.repository.Dao.Chat;
import edu.java.bot.repository.Dao.SiteUrl;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    final ChatRepository chatRepository;

    public Optional<Chat> findChat(Long id) {
        return chatRepository.findById(id);
    }

    public boolean isRegistered(Long id) {
        return chatRepository.findById(id).isPresent();
    }

    public boolean register(Long id) {
        return chatRepository.insertById(id);
    }

    public Optional<Chat> updateBotState(Long id, BotState state) {
        if (chatRepository.findById(id).isPresent()) {
            Chat chat = chatRepository.findById(id).get();
            chat.setBotState(state);
            chatRepository.insertByChat(chat);
        }
        return chatRepository.findById(id);
    }

    public Optional<Chat> trackUrl(Long id, String url) {
        if (chatRepository.findById(id).isPresent()) {
            Chat chat = chatRepository.findById(id).get();
            if (chat.trackedSites.stream().noneMatch(x -> x.url.equals(url))) {
                chat.trackedSites.add(new SiteUrl(url));
            }
            chatRepository.insertByChat(chat);
        }
        return chatRepository.findById(id);
    }

    public Optional<Chat> untrackUrl(Long id, String url) {
        if (chatRepository.findById(id).isPresent()) {
            Chat chat = chatRepository.findById(id).get();
            chatRepository.insertByChat(new Chat(chat.botState, chat.chatId,
                chat.trackedSites.stream().filter(x -> !x.url.equals(url)).collect(Collectors.toList())
            ));
        }
        return chatRepository.findById(id);
    }

}
