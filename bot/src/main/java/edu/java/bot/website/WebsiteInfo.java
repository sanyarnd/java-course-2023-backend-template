package edu.java.bot.website;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebsiteInfo {
    GITHUB("github.com"),
    STACK_OVERFLOW("stackoverflow.com");

    private final String domain;
}
