package edu.java.bot.user;

import edu.java.bot.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testUserRegistration() {
        Long userId = 1L;
        assertFalse(userService.isRegistered(userId));

        userService.registerUser(userId);
        assertTrue(userService.isRegistered(userId));
    }

    @Test
    void testUserIsNotRegistered() {
        Long userId = 2L;
        assertFalse(userService.isRegistered(userId));
    }

    @Test
    void testMultipleUsersRegistration() {
        Long firstUserId = 1L;
        Long secondUserId = 2L;

        userService.registerUser(firstUserId);
        userService.registerUser(secondUserId);

        assertTrue(userService.isRegistered(firstUserId));
        assertTrue(userService.isRegistered(secondUserId));
    }

    @Test
    void testRegisterUserTwice() {
        Long userId = 1L;
        userService.registerUser(userId);
        assertDoesNotThrow(() -> userService.registerUser(userId));

        assertTrue(userService.isRegistered(userId));
    }
}
