package edu.java.bot.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;

public class LinksTest {

    @ParameterizedTest
    @ValueSource(strings = {"https://ya.ru/", "https://github.com/0SNP0/java-course-2023-backend-0SNP0"})
    public void correctLink(String text) {
        assertThat(Validation.isLink(text)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa.aa", "qwerty://abc.de", "http://a b.c"})
    public void incorrectLink(String text) {
        assertThat(Validation.isLink(text)).isFalse();
    }
}
