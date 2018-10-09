package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidEmail(null));

        // blank email
        assertFalse(Priority.isValidEmail("")); // empty string
        assertFalse(Priority.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Priority.isValidEmail("@example.com")); // missing local part
        assertFalse(Priority.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Priority.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Priority.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Priority.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Priority.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Priority.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Priority.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Priority.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Priority.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Priority.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Priority.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Priority.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Priority.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Priority.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Priority.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Priority.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Priority.isValidEmail("a@bc")); // minimal
        assertTrue(Priority.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Priority.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Priority.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Priority.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Priority.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Priority.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
