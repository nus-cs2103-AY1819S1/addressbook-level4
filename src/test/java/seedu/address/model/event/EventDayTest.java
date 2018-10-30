package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.DayOfWeek;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventDayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventDay(null));
    }

    @Test
    public void equals() {

        EventDay eventDay = new EventDay(DayOfWeek.MONDAY);

        // null
        assertNotEquals(eventDay, null);

        // different types
        assertNotEquals(eventDay, " ");

        // different values
        assertNotEquals(eventDay, new EventDay(DayOfWeek.TUESDAY));

        // same object
        assertEquals(eventDay, eventDay);

        // same values
        assertEquals(eventDay, new EventDay(DayOfWeek.MONDAY));

    }
}
