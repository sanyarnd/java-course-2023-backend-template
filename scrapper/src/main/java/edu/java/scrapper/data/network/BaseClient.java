package edu.java.scrapper.data.network;

import edu.java.core.exception.LinkIsUnreachable;
import edu.java.scrapper.data.db.entity.Link;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jetbrains.annotations.NotNull;

public abstract class BaseClient {
    protected final List<Pattern> handledUrlPatterns;

    protected BaseClient(List<String> urls) throws PatternSyntaxException {
        this.handledUrlPatterns = urls.stream().map(Pattern::compile).toList();
    }

    public final @NotNull Boolean canHandle(@NotNull Link link) {
        return handledUrlPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(link.getUrl()).matches());
    }

    protected final @NotNull List<String> extractDataTokensFromLink(@NotNull Link link) {
        Matcher matcher = handledUrlPatterns.stream()
                .filter(pattern -> pattern.matcher(link.getUrl()).matches())
                .findAny()
                .orElseThrow(LinkIsUnreachable::new)
                .matcher(link.getUrl());
        if (!matcher.matches()) throw new IllegalStateException("Dude...");
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < matcher.groupCount() + 1; i++) {
            tokens.add(matcher.group(i));
        }
        return tokens;
    }

    public abstract Link handle(Link link) throws LinkIsUnreachable;
}
