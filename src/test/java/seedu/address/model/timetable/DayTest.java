package seedu.address.model.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Unit test for the Day model class.
 */
public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day(" "));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day("Thur"));
    }

    @Test
    public void isValidDay() {
        // null day
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid day
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay(" ")); // spaces only
        assertFalse(Day.isValidDay("^")); // only numbers are allowed.
        assertFalse(Day.isValidDay("hello")); // not a day
        assertFalse(Day.isValidDay("Thurs")); // not a valid day syntax.

        // valid day
        assertTrue(Day.isValidDay("MON"));
        assertTrue(Day.isValidDay("tuE"));
        assertTrue(Day.isValidDay("wed")); // normal non-capitalised characters
        assertTrue(Day.isValidDay("tHu")); // some capitalised characters
        assertTrue(Day.isValidDay("FRI"));
        assertTrue(Day.isValidDay("SAt"));
        assertTrue(Day.isValidDay("SUN"));
    }

}
