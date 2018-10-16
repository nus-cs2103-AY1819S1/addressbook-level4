package seedu.address.model.test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TimeLimitTest {

    @Test
    public void valid_timeLimit() {
        assertTrue(TimeLimit.isValidTimeLimit("1"));
        assertTrue(TimeLimit.isValidTimeLimit("2"));
        assertTrue(TimeLimit.isValidTimeLimit("100"));
    }

    @Test
    public void invalid_timeLimit() {
        assertFalse(TimeLimit.isValidTimeLimit("0"));
        assertFalse(TimeLimit.isValidTimeLimit("-1"));
        assertFalse(TimeLimit.isValidTimeLimit("-100"));

        Assert.assertThrows(NullPointerException.class, () -> new TimeLimit(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new TimeLimit("-1"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new TimeLimit("-100"));
    }

    @Test
    public void equals() {
        TimeLimit timeLimit = new TimeLimit("2");

        // same values -> returns true
        TimeLimit timeLimitCopy = new TimeLimit(String.valueOf(timeLimit.getValue()));
        assertTrue(timeLimit.equals(timeLimitCopy));

        // same object -> returns true
        assertTrue(timeLimit.equals(timeLimit));

        // null -> returns false
        assertFalse(timeLimit.equals(null));

        // different type -> returns false
        assertFalse(timeLimit.equals(5));

        // different cards being tested -> returns false
        assertFalse(timeLimit.equals(new TimeLimit("3")));

    }

}
