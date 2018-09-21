package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author snajef
/**
 * Test driver class for the Nric POJO class.
 * @author Darien Chong
 *
 */
public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidName = "S666AAA";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidName));
    }

    @Test
    public void isValidNric() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("^")); // only non-alphanumeric characters
        assertFalse(Nric.isValidNric("S***A")); // contains non-alphanumeric characters
        assertFalse(Nric.isValidNric("s1234567b")); // lower case
        assertFalse(Nric.isValidNric("1234567X")); // missing suffix

        // valid nric
        assertTrue(Nric.isValidNric("S1234567A")); // S suffix
        assertTrue(Nric.isValidNric("T1234567B")); // T suffix
        assertTrue(Nric.isValidNric("G0000000G")); // G suffix
        assertTrue(Nric.isValidNric("F1234567C")); // F suffix
    }
}
