package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MeaningTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Meaning(null));
    }

    @Test
    public void constructor_invalidMeaning_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meaning(invalidAddress));
    }

    @Test
    public void isValidMeaning() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Meaning.isValidMeaning(null));

        // invalid addresses
        assertFalse(Meaning.isValidMeaning("")); // empty string
        assertFalse(Meaning.isValidMeaning(" ")); // spaces only

        // valid addresses
        assertTrue(Meaning.isValidMeaning("Happy"));
        assertTrue(Meaning.isValidMeaning("-")); // one character
        assertTrue(Meaning.isValidMeaning("Takes a long time to do this crap")); // long meaning
    }
}
