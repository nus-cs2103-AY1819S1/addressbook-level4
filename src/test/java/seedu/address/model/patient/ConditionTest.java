package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

public class ConditionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        String invalidCondition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(invalidCondition));
    }

    @Test
    public void isValidCondition() {
        // null Condition
        Assert.assertThrows(NullPointerException.class, () -> Condition.isValidCondition(null));

        // invalid Condition
        assertFalse(Condition.isValidCondition("")); // empty string
        assertFalse(Condition.isValidCondition(" ")); // spaces only
        assertFalse(Condition.isValidCondition("^")); // only non-alphanumeric characters
        assertFalse(Condition.isValidCondition("health*")); // contains non-alphanumeric characters

        // valid Condition
        assertTrue(Condition.isValidCondition("super healthy")); // alphabets only
        assertTrue(Condition.isValidCondition("12345")); // numbers only
        assertTrue(Condition.isValidCondition("fever for 2 days")); // alphanumeric characters
        assertTrue(Condition.isValidCondition("Cough")); // with capital letters
        assertTrue(Condition.isValidCondition("Cannot sleep well before meeting")); // long names
    }
}
