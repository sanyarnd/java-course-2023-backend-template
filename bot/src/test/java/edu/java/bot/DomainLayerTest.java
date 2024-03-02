package edu.java.bot;

import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.TrackerRepository;
import edu.java.bot.domain.links.ViewLinksResponse;
import edu.java.bot.domain.links.ViewLinksUseCase;
import edu.java.bot.domain.register.RegisterUserResponse;
import edu.java.bot.domain.register.RegisterUserUseCase;
import edu.java.bot.domain.subscribe.TrackLinkResponse;
import edu.java.bot.domain.subscribe.TrackLinkUseCase;
import edu.java.bot.domain.unsubscribe.UntrackLinkResponse;
import edu.java.bot.domain.unsubscribe.UntrackLinkUseCase;
import edu.java.bot.model.LinkAlreadyTrackedException;
import edu.java.bot.model.LinkIsNotTrackedException;
import edu.java.bot.model.UserAlreadyRegisteredException;
import edu.java.bot.model.UserIsNotRegisteredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DomainLayerTest {
    @Mock
    public TrackerRepository repository;

    @Mock
    public User okUser;

    @Mock
    public User notOkUser;

    @BeforeAll
    public static void log() {
        System.out.println("Log " + DomainLayerTest.class.getName());
    }

    @BeforeEach
    public void setupUsernames() {
        Mockito.lenient().when(okUser.username()).thenReturn("AlexCawl");
        Mockito.lenient().when(notOkUser.username()).thenReturn("mswork6");
    }

    @Nested
    public class RegisterUserTest {
        @BeforeEach
        public void setupInitUser() {
            Mockito.lenient().doThrow(UserAlreadyRegisteredException.class).when(repository).registerUser("AlexCawl");
            Mockito.lenient().doNothing().when(repository).registerUser("mswork6");
        }

        @Test
        public void userRegistrationOk() {
            var useCase = new RegisterUserUseCase(repository);
            var response = useCase.registerUser(notOkUser);
            Assertions.assertInstanceOf(RegisterUserResponse.Ok.class, response);
        }

        @Test
        public void userRegistrationNotOk() {
            var useCase = new RegisterUserUseCase(repository);
            var response = useCase.registerUser(okUser);
            Assertions.assertInstanceOf(RegisterUserResponse.AlreadyRegistered.class, response);
        }
    }

    @Nested
    public class TrackLinkTest {
        @BeforeEach
        public void setupTrackLinks() {
            Mockito.lenient().doThrow(UserIsNotRegisteredException.class).when(repository).subscribeToLinkUpdates("mswork6", "onlyfans.com");
            Mockito.lenient().doThrow(LinkAlreadyTrackedException.class).when(repository).subscribeToLinkUpdates("AlexCawl", "github.com");
        }

        @Test
        public void checkUserIsNotRegisteredYet() {
            var useCase = new TrackLinkUseCase(repository);
            var response = useCase.trackLink(notOkUser, "onlyfans.com");
            Assertions.assertInstanceOf(TrackLinkResponse.UserIsNotDefined.class, response);
        }

        @Test
        public void checkLinkIsAlreadyTracked() {
            var useCase = new TrackLinkUseCase(repository);
            var response = useCase.trackLink(okUser, "github.com");
            Assertions.assertInstanceOf(TrackLinkResponse.AlreadyRegistered.class, response);
        }
    }


    @Nested
    public class UntrackLinkTest {
        @BeforeEach
        public void setupUntrackLinks() {
            Mockito.lenient().doThrow(UserIsNotRegisteredException.class).when(repository).unsubscribeToLinkUpdates("mswork6", "onlyfans.com");
            Mockito.lenient().doThrow(LinkIsNotTrackedException.class).when(repository).unsubscribeToLinkUpdates("AlexCawl", "onlyfans.com");
        }

        @Test
        public void checkUserIsNotRegisteredYet() {
            var useCase = new UntrackLinkUseCase(repository);
            var response = useCase.untrackLink(notOkUser, "onlyfans.com");
            Assertions.assertInstanceOf(UntrackLinkResponse.UserIsNotDefined.class, response);
        }

        @Test
        public void checkLinkIsAlreadyTracked() {
            var useCase = new UntrackLinkUseCase(repository);
            var response = useCase.untrackLink(okUser, "onlyfans.com");
            Assertions.assertInstanceOf(UntrackLinkResponse.IsNotRegistered.class, response);
        }
    }


    @Nested
    public class ViewLinksTest {
        @BeforeEach
        public void setupGetSubscriptions() {
            Mockito.lenient().when(repository.getUserSubscriptions("AlexCawl")).thenReturn(List.of("stackoverflow.com", "github.com"));
            Mockito.lenient().when(repository.getUserSubscriptions("mswork6")).thenThrow(UserIsNotRegisteredException.class);
        }

        @Test
        public void checkLinksOk() {
            var useCase = new ViewLinksUseCase(repository);
            var response = useCase.viewLinks(okUser);
            Assertions.assertInstanceOf(ViewLinksResponse.Ok.class, response);
        }

        @Test
        public void checkLinksNotOk() {
            var useCase = new ViewLinksUseCase(repository);
            var response = useCase.viewLinks(notOkUser);
            Assertions.assertInstanceOf(ViewLinksResponse.UserIsNotDefined.class, response);
        }
    }
}
