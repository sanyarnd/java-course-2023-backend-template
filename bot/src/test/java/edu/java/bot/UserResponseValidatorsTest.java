package edu.java.bot;

import edu.java.bot.util.UserResponseValidators;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserResponseValidatorsTest {

    @Test
    void testListIndexValidate_ValidIndex_ReturnsTrue() {
        List<String> list = Arrays.asList("item1", "item2", "item3");
        String validIndex = "1";

        boolean isValid = UserResponseValidators.listIndexValidate(validIndex, list);

        Assertions.assertTrue(isValid);
    }

    @Test
    void testListIndexValidate_InvalidIndex_ReturnsFalse() {
        List<String> list = Arrays.asList("item1", "item2", "item3");
        String invalidIndex = "5";

        boolean isValid = UserResponseValidators.listIndexValidate(invalidIndex, list);

        Assertions.assertFalse(isValid);
    }

    @Test
    void testListIndexValidate_NonNumericIndex_ReturnsFalse() {
        List<String> list = Arrays.asList("item1", "item2", "item3");
        String nonNumericIndex = "abc";

        boolean isValid = UserResponseValidators.listIndexValidate(nonNumericIndex, list);

        Assertions.assertFalse(isValid);
    }

    @Test
    void testListIndexValidate_NullList_ReturnsFalse() {
        List<String> list = null;
        String index = "1";

        boolean isValid = UserResponseValidators.listIndexValidate(index, list);

        Assertions.assertFalse(isValid);
    }
}
