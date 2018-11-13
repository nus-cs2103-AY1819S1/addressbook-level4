package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peanut*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("tom yum")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("tom yum no 1")); // alphanumeric characters
        assertTrue(Name.isValidName("Curry Chicken")); // with capital letters
        assertTrue(Name.isValidName("Very Spicy Curry Chicken in Singapore 2nd Street")); // long names
    }
}
