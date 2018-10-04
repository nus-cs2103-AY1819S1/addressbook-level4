package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityValueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PriorityValue(null));
    }

    @Test
    public void constructor_invalidPriorityValue_throwsIllegalArgumentException() {
        String invalidPriorityValue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PriorityValue(invalidPriorityValue));
    }

    @Test
    public void isValidPriorityValue() {
        // null priority value
        Assert.assertThrows(NullPointerException.class, () -> PriorityValue.isValidPriorityValue(null));

        // non-positive-integral values
        assertFalse(PriorityValue.isValidPriorityValue("0"));
        assertFalse(PriorityValue.isValidPriorityValue("-2"));
        assertFalse(PriorityValue.isValidPriorityValue("01")); // no leading zeroes allowed
        assertFalse(PriorityValue.isValidPriorityValue("-24"));
        assertFalse(PriorityValue.isValidPriorityValue("asdf"));
        assertFalse(PriorityValue.isValidPriorityValue("asdf@example.com"));
        assertFalse(PriorityValue.isValidPriorityValue("None"));
        assertFalse(PriorityValue.isValidPriorityValue("-234567890987654345678965432345678987654321345678987654"));
        assertFalse(PriorityValue.isValidPriorityValue("-Infinite"));
        assertFalse(PriorityValue.isValidPriorityValue("HIGH"));
        assertFalse(PriorityValue.isValidPriorityValue("Medium"));
        assertFalse(PriorityValue.isValidPriorityValue("Immediate"));

        // valid priority value
        assertTrue(PriorityValue.isValidPriorityValue("1"));
        assertTrue(PriorityValue.isValidPriorityValue("2")); // minimal
        assertTrue(PriorityValue.isValidPriorityValue("123456789876543212345678765432234567")); // long
    }
}
