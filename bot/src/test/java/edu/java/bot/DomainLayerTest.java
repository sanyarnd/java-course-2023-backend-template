package edu.java.bot;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.LinkTrackerRepository;
import edu.java.bot.data.UserAuthRepository;
import edu.java.bot.domain.*;
import edu.java.core.exception.ApiErrorException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DomainLayerTest {
    @Mock
    public LinkTrackerRepository linkTrackerRepository;

    @Mock
    public UserAuthRepository userAuthRepository;

    @Mock
    public User okUser;

    @Mock
    public User notOkUser;

    @BeforeEach
    public void setupUsernames() {
        Mockito.lenient().when(okUser.id()).thenReturn(123L);
        Mockito.lenient().when(notOkUser.id()).thenReturn(456L);
    }

    @Nested
    public class RegisterUserTest {
        @BeforeEach
        public void setupInitUser() {
            Mockito.lenient().doThrow(ApiErrorException.class).when(userAuthRepository).registerUser(123L);
            Mockito.lenient().doNothing().when(userAuthRepository).registerUser(456L);
        }

        @Test
        public void userRegistrationOk() {
            var useCase = new RegisterUserUseCase(userAuthRepository);
            var response = useCase.registerUser(notOkUser);
            Assertions.assertInstanceOf(TelegramResponse.class, response);
        }

        @Test
        public void userRegistrationNotOk() {
            var useCase = new RegisterUserUseCase(userAuthRepository);
            var response = useCase.registerUser(okUser);
            Assertions.assertInstanceOf(ErrorTelegramResponse.class, response);
        }
    }

    @Nested
    public class TrackLinkTest {
        @BeforeEach
        public void setupTrackLinks() {
            Mockito.lenient().doThrow(ApiErrorException.class).when(linkTrackerRepository)
                .setLinkTracked(456L, "onlyfans.com");
        }

        @Test
        public void checkUserIsNotRegisteredYet() {
            var useCase = new TrackLinkUseCase(linkTrackerRepository);
            var response = useCase.trackLink(notOkUser, "onlyfans.com");
            Assertions.assertInstanceOf(ErrorTelegramResponse.class, response);
        }
    }

    @Nested
    public class UntrackLinkTest {
        @BeforeEach
        public void setupUntrackLinks() {
            Mockito.lenient().doThrow(ApiErrorException.class).when(linkTrackerRepository)
                .setLinkUntracked(456L, "onlyfans.com");
        }

        @Test
        public void checkUserIsNotRegisteredYet() {
            var useCase = new UntrackLinkUseCase(linkTrackerRepository);
            var response = useCase.untrackLink(notOkUser, "onlyfans.com");
            Assertions.assertInstanceOf(ErrorTelegramResponse.class, response);
        }
    }

    @Nested
    public class ViewLinksTest {
        @BeforeEach
        public void setupGetSubscriptions() {
            Mockito.lenient().when(linkTrackerRepository.getUserTrackedLinks(123L))
                .thenReturn(List.of("stackoverflow.com", "github.com"));
            Mockito.lenient().when(linkTrackerRepository.getUserTrackedLinks(456L))
                .thenThrow(ApiErrorException.class);
        }

        @Test
        public void checkLinksOk() {
            var useCase = new ViewLinksUseCase(linkTrackerRepository);
            var response = useCase.viewLinks(okUser);
            Assertions.assertInstanceOf(TelegramResponse.class, response);
        }

        @Test
        public void checkLinksNotOk() {
            var useCase = new ViewLinksUseCase(linkTrackerRepository);
            var response = useCase.viewLinks(notOkUser);
            Assertions.assertInstanceOf(ErrorTelegramResponse.class, response);
        }
    }
}
