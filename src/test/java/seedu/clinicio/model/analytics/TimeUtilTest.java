package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.model.analytics.util.TimeUtil;
import seedu.clinicio.model.appointment.Time;

/**
 * Contains tests for the TimeUtil class.
 * The TimeUtil class assumes all times occur in the same day.
 */
public class TimeUtilTest {

    @Test
    public void isAfterOrEqual() {
        // Test equal times
        Time timeEqual = new Time(10, 10);
        assertTrue(TimeUtil.isAfterOrEqual(timeEqual, timeEqual));

        // Test after times
        Time testTrue = new Time(13, 53);
        Time after = new Time(13, 54);
        Time after2 = new Time(14, 52);
        Time after3 = new Time(23, 59);
        assertTrue(TimeUtil.isAfterOrEqual(after, testTrue));
        assertTrue(TimeUtil.isAfterOrEqual(after2, testTrue));
        assertTrue(TimeUtil.isAfterOrEqual(after3, testTrue));

        // Test before times
        Time testFalse = new Time(23, 59);
        Time before = new Time(0, 0);
        Time before2 = new Time(15, 42);
        assertFalse(TimeUtil.isAfterOrEqual(before, testFalse));
        assertFalse(TimeUtil.isAfterOrEqual(before2, testFalse));
    }

    @Test
    public void isBeforeOrEqual() {
        // Test equal times
        Time timeEqual = new Time(10, 10);
        assertTrue(TimeUtil.isBeforeOrEqual(timeEqual, timeEqual));

        // Test before times
        Time testTrue = new Time(16, 12);
        Time before = new Time(11, 22);
        Time before2 = new Time(0, 0);
        assertTrue(TimeUtil.isBeforeOrEqual(before, testTrue));
        assertTrue(TimeUtil.isBeforeOrEqual(before2, testTrue));

        // Test after times
        Time testFalse = new Time(0, 0);
        Time after = new Time(9, 1);
        Time after2 = new Time(23, 59);
        assertFalse(TimeUtil.isBeforeOrEqual(after, testFalse));
        assertFalse(TimeUtil.isBeforeOrEqual(after2, testFalse));
    }
}
