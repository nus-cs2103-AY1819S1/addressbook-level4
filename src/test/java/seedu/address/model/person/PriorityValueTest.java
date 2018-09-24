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

        // blank priority value
        assertFalse(PriorityValue.isValidPriorityValue("")); // empty string
        assertFalse(PriorityValue.isValidPriorityValue(" ")); // spaces only

        // missing parts
        assertFalse(PriorityValue.isValidPriorityValue("@example.com")); // missing local part
        assertFalse(PriorityValue.isValidPriorityValue("peterjackexample.com")); // missing '@' symbol
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@-")); // invalid domain name
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(PriorityValue.isValidPriorityValue("peter jack@example.com")); // spaces in local part
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(PriorityValue.isValidPriorityValue(" peterjack@example.com")); // leading space
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@example.com ")); // trailing space
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@@example.com")); // double '@' symbol
        assertFalse(PriorityValue.isValidPriorityValue("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@.example.com")); // domain name starts with a period
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@example.com.")); // domain name ends with a period
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(PriorityValue.isValidPriorityValue("peterjack@example.com-")); // domain name ends with a hyphen

        // valid priority value
        assertTrue(PriorityValue.isValidPriorityValue("PeterJack_1190@example.com"));
        assertTrue(PriorityValue.isValidPriorityValue("a@bc")); // minimal
        assertTrue(PriorityValue.isValidPriorityValue("test@localhost")); // alphabets only
        assertTrue(PriorityValue.isValidPriorityValue("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(PriorityValue.isValidPriorityValue("123@145")); // numeric local part and domain name
        assertTrue(PriorityValue.isValidPriorityValue("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(PriorityValue.isValidPriorityValue("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(PriorityValue.isValidPriorityValue("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
