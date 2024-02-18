package edu.java.bot.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Set<Long> registeredUsers = new HashSet<>();
    private final Map<Long, UserState> userStates = new HashMap<>();

    public boolean isRegistered(Long userId) {
        return registeredUsers.contains(userId);
    }

    public void registerUser(Long userId) {
        registeredUsers.add(userId);
    }

    public void setUserState(Long userId, UserState state) {
        userStates.put(userId, state);
    }

    public UserState getUserState(Long userId) {
        return userStates.getOrDefault(userId, UserState.NONE);
    }
}

