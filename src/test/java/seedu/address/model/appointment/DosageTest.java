package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DosageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Dosage(null));
    }

    @Test
    public void constructor_invalidDosage_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dosage(invalidAmount));
    }

    @Test
    public void isValidDosage() {
        // null dosage
        Assert.assertThrows(NullPointerException.class, () -> Dosage.isValidDosage(null));

        // invalid dosage
        assertFalse(Dosage.isValidDosage("")); // empty string
        assertFalse(Dosage.isValidDosage(" ")); // spaces only
        assertFalse(Dosage.isValidDosage("^")); // only non-alphanumeric characters
        assertFalse(Dosage.isValidDosage("peter*")); // contains non-alphanumeric characters
        assertFalse(Dosage.isValidDosage("peterjack")); // alphabets only
        assertFalse(Dosage.isValidDosage("123 123")); // number with space

        // valid dosage
        assertTrue(Dosage.isValidDosage("12345")); // numbers only
        assertTrue(Dosage.isValidDosage("123412341241241234")); // long numbers
    }
}
