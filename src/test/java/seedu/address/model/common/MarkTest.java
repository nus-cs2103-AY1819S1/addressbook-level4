package seedu.address.model.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Mark(null));
    }

    @Test
    public void constructor_invalidMark_throwsIllegalArgumentException() {
        String invalidMark = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Mark(invalidMark));
    }

    @Test
    public void isValidMark() {
        // null mark
        Assert.assertThrows(NullPointerException.class, () -> Mark.isValid(null));

        // invalid mark
        assertFalse(Mark.isValid("")); // empty string
        assertFalse(Mark.isValid(" ")); // spaces only
        assertFalse(Mark.isValid("-12asdf")); // non number at the back
        assertFalse(Mark.isValid("asdf-12")); // non number at the front
        assertFalse(Mark.isValid("-1asdf2")); // non number at the middle

        // valid mark
        assertTrue(Mark.isValid("12"));
        assertTrue(Mark.isValid("-12")); // negative
        assertTrue(Mark.isValid("12.5")); // non integer
    }

    @Test
    public void markComparison() {
        Mark mark1 = new Mark("-13");
        Mark mark2 = new Mark("-0.5");
        Mark mark3 = new Mark("3");

        assertEquals(mark1.compareTo(mark2), -1);
        assertEquals(mark1.compareTo(mark3), -1);
        assertEquals(mark2.compareTo(mark3), -1);
        assertEquals(mark1.compareTo(mark1), 0);
        assertEquals(mark2.compareTo(mark2), 0);
        assertEquals(mark3.compareTo(mark3), 0);
    }

    @Test
    public void markValue() {
        Mark mark1 = new Mark("-13");
        Mark mark2 = new Mark("-0.5");
        Mark mark3 = new Mark("3");

        assertEquals(mark1.getValue(), Double.valueOf(-13));
        assertEquals(mark2.getValue(), Double.valueOf(-0.5));
        assertEquals(mark3.getValue(), Double.valueOf(3));
    }
}
