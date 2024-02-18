package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Command;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class HelpCommand implements Command {
    private final int FORMAT_LENGTH = 6;
    @Override
    public String command() {
        return getCommandList();
    }

    @Override
    public String description() {
        return "/help — shows available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), command());
    }

    private String getCommandList() {
        List<Class<?>> classes = getClasses("edu.java.bot.commands");
        StringBuilder result = new StringBuilder();
        for (Class<?> clazz : classes) {
            try {
                String s = (String) clazz.getMethod("description").invoke(clazz.newInstance());
                result.append(s);
                result.append("\n");
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
                     | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

    private List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.getFile());

                if (file.isDirectory()) {
                    for (File f : Objects.requireNonNull(file.listFiles())) {
                        if (f.getName().endsWith(".class")) {
                            String className =
                                packageName + "." + f.getName().substring(0, f.getName().length() - FORMAT_LENGTH);
                            classes.add(Class.forName(className));
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        return classes;
    }
}
