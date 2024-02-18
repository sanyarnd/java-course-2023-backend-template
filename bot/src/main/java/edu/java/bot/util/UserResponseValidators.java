package edu.java.bot.util;

import java.util.List;
import java.util.Objects;

public class UserResponseValidators {

    private UserResponseValidators() {
    }

    public static boolean listIndexValidate(String index, List<?> list) {
        if (Objects.isNull(list)) {
            return false;
        }
        int i;
        try {
            i = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            return false;
        }
        return i >= 0 && i < list.size();

    }
}
