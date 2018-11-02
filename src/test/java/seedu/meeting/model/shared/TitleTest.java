package seedu.meeting.model.shared;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.meeting.testutil.Assert;

/**
 * {@author Derek-Hardy}
 */
public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("project*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("project gemini")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("group the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Group Developer")); // with capital letters
        assertTrue(Title.isValidTitle("Project Group for the 3rd presentation")); // long names
    }
}
