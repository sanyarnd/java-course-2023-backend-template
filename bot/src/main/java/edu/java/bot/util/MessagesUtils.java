package edu.java.bot.util;


import lombok.experimental.UtilityClass;
import static edu.java.bot.command.Command.HELP;
import static edu.java.bot.command.Command.LIST;
import static edu.java.bot.command.Command.TRACK;
import static edu.java.bot.command.Command.UNTRACK;
@UtilityClass
public class MessagesUtils {

    public static final String LINK_HAS_BEEN_UNTRACKED = "Ссылка больше не отслеживается.";
    public static final String CHOOSE_LINK_TO_UNTRACK = "Выберите ссылку, у которой нужно прекратить отслеживание: ";
    public static final String NO_TRACKED_LINKS = "Отслеживаемых ссылок нет 😥. Добавьте ссылку с помощью команды /track.";
    public static final String WELCOME_MESSAGE = """
            <b>Привет! 😊</b>
            Это бот для отслеживания изменений на GitHub и Stack Overflow. 🚀
            Справка по командам - /help""";
    public static final String TRACKED_LINKS = "Отслеживаемые ссылки: ";
    public static final String HELP_MESSAGE = """
            <b>Доступные команды:</b>
            %s - %s
            %s - %s
            %s - %s
            %s - %s""".formatted(
        TRACK.getCommandName(), TRACK.getCommandDescription().toLowerCase(),
        UNTRACK.getCommandName(), UNTRACK.getCommandDescription().toLowerCase(),
        LIST.getCommandName(), LIST.getCommandDescription().toLowerCase(),
        HELP.getCommandName(), HELP.getCommandDescription().toLowerCase()
    );
    public static final String ERROR_MESSAGE = """
            <b>Ошибка:</b> Команда не существует. ❌
            Пожалуйста, воспользуйтесь командой /help для получения списка доступных команд.
            """;
    public static final String ONLY_TEXT_TO_SEND = "Для отправки доступен только текст!";
    public static final String TRACK_EXAMPLE = "Отправьте команду в формате: /track <ссылка>.";
    public static final String LINK_IS_TRACKED = "Ссылка %s теперь отслеживается ✔️";
    public static final String HTTPS_PREFIX = "https://";
    public static final String HTTP_PREFIX = "http://";
    public static final String LINK_SHOULD_STARTS_WITH_HTTP = "Ссылка должна начинаться с " + HTTPS_PREFIX + " или " + HTTP_PREFIX + "❗";
}
