package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.word.Meaning;
import seedu.address.testutil.Assert;

public class MeaningTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Meaning(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meaning(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Meaning.isValidMeaning(null));

        // invalid addresses
        assertFalse(Meaning.isValidMeaning("")); // empty string
        assertFalse(Meaning.isValidMeaning(" ")); // spaces only

        // valid addresses
        assertTrue(Meaning.isValidMeaning("Blk 456, Den Road, #01-355"));
        assertTrue(Meaning.isValidMeaning("-")); // one character
        assertTrue(Meaning.isValidMeaning("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
