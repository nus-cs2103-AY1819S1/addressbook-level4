package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> EventDescription.isValidDescription(null));

        // invalid description
        assertFalse(EventDescription.isValidDescription("")); // empty string
        assertFalse(EventDescription.isValidDescription(" ")); // spaces only
        assertFalse(EventDescription.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(EventDescription.isValidDescription("consultation*")); // contains non-alphanumeric characters

        // valid description
        assertTrue(EventDescription.isValidDescription("consultation")); // alphabets only
        assertTrue(EventDescription.isValidDescription("12345")); // numbers only
        assertTrue(EventDescription.isValidDescription("1st meeting")); // alphanumeric characters
        assertTrue(EventDescription.isValidDescription("MeeTing 123")); // with capital letters
        assertTrue(EventDescription.isValidDescription("Consultation at the Nearby Polyclinic")); // long names
    }
}
