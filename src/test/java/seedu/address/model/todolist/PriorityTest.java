package seedu.address.model.todolist;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "a";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValid(null));

        // invalid priority
        assertFalse(Priority.isValid("")); // empty string
        assertFalse(Priority.isValid("abcde")); // include alphabets
        assertFalse(Priority.isValid("#$%")); //include symbols
        assertFalse(Priority.isValid("4")); // number larger than 3
        assertFalse(Priority.isValid("20")); // number larger than 3
        assertFalse(Priority.isValid("0")); // number smaller than 0

        // valid priority
        assertTrue(Priority.isValid("1")); // high priority
        assertTrue(Priority.isValid("2")); // medium priority
        assertTrue(Priority.isValid("3")); // low priority
    }
}
