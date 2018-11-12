package seedu.address.model.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Unit test for the Time model class.
 */
public class WeekTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Week(null));
    }

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Week(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Week(" "));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Week("One"));
    }

    @Test
    public void isValidWeek() {
        // null week
        Assert.assertThrows(NullPointerException.class, () -> Week.isValidWeek(null));

        // invalid week
        assertFalse(Week.isValidWeek("")); // empty string.
        assertFalse(Week.isValidWeek(" ")); // spaces only.
        assertFalse(Week.isValidWeek("^")); // only numbers are allowed.
        assertFalse(Week.isValidWeek("hello")); // not a week.
        assertFalse(Week.isValidWeek("one")); // not a valid week syntax.
        assertFalse(Week.isValidWeek("READING")); // no caps.
        assertFalse(Week.isValidWeek("Recess")); // no caps allowed.

        // valid week
        assertTrue(Week.isValidWeek("1"));
        assertTrue(Week.isValidWeek("13"));
        assertTrue(Week.isValidWeek("15")); // normal non-capitalised characters
        assertTrue(Week.isValidWeek("14")); // some capitalised characters
        assertTrue(Week.isValidWeek("6"));
        assertTrue(Week.isValidWeek("reading"));
        assertTrue(Week.isValidWeek("recess"));
    }

}
