package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ssp.scheduleplanner.testutil.Assert;

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
        
        // null priority
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // blank priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // invalid priority
        assertFalse(Priority.isValidPriority("a")); // alphabet is invalid
        assertFalse(Priority.isValidPriority("@")); // special character is invalid
        assertFalse(Priority.isValidPriority("~")); // special character is invalid
        assertFalse(Priority.isValidPriority("4")); // numerical value outside 1,2,3 is invalid.
        assertFalse(Priority.isValidPriority("11")); // valid numerical values but more than one digit
        assertFalse(Priority.isValidPriority("22")); // valid numerical values but more than one digit
        assertFalse(Priority.isValidPriority("33")); // valid numerical values but more than one digit
        assertFalse(Priority.isValidPriority("a1")); // mix of alphanumerical
        assertFalse(Priority.isValidPriority("a@")); // mix of alphabet and special character
        assertFalse(Priority.isValidPriority("1@")); // mix of numerical and special character
        assertFalse(Priority.isValidPriority("a1@")); // mix of alphanumerical and special character
        assertFalse(Priority.isValidPriority("0")); // zero is invalid
        assertFalse(Priority.isValidPriority("-1")); //valid numerical value in negative form is invalid
        assertFalse(Priority.isValidPriority("-2")); //valid numerical value in negative form is invalid
        assertFalse(Priority.isValidPriority("-3")); //valid numerical value in negative form is invalid

        // valid priority
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));

    }
}
