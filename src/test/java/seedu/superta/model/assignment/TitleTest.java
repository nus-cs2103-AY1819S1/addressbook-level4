package seedu.superta.model.assignment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.superta.testutil.Assert;

public class TitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null title
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("lab1*")); // contains non-alphanumeric characters

        // valid title
        assertTrue(Title.isValidTitle("tutorial")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("tutorial week 1")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Homework")); // with capital letters
        assertTrue(Title.isValidTitle("CS2013T Tutorial Questions")); // long names
    }
}
