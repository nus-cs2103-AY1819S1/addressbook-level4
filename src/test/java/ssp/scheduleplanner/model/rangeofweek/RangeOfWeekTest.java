package ssp.scheduleplanner.model.rangeofweek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RangeOfWeekTest {
    private static final String VALID_START_OF_WEEK = "130818";
    private static final String VALID_END_OF_WEEK = "190818";
    private static final String VALID_DESCRIPTION = "Week 1";

    @Test
    public void toString_equal() {
        RangeOfWeek range = new RangeOfWeek(VALID_START_OF_WEEK,
                VALID_END_OF_WEEK, VALID_DESCRIPTION);
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start of Week: ")
                .append(VALID_START_OF_WEEK)
                .append(" End of Week: ")
                .append(VALID_END_OF_WEEK)
                .append(" Description: ")
                .append(VALID_DESCRIPTION);

        assertEquals(range.toString(), builder.toString());
    }

    @Test
    public void equals_equal() {
        RangeOfWeek range = new RangeOfWeek(VALID_START_OF_WEEK,
                VALID_END_OF_WEEK, VALID_DESCRIPTION);
        RangeOfWeek checkRange = range;

        assertEquals(range, checkRange);
        assertTrue(range.equals(checkRange));
        assertTrue(checkRange.equals(range));
    }

    @Test
    public void hashCode_equal() {
        RangeOfWeek range = new RangeOfWeek(VALID_START_OF_WEEK,
                VALID_END_OF_WEEK, VALID_DESCRIPTION);
        RangeOfWeek checkRange = range;

        assertEquals(range.hashCode(), checkRange.hashCode());
    }
}
