package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.scheduler.testutil.Assert;

public class DateTimeTest {
    private DateTime datetime1 = new DateTime(LocalDateTime.of(2020, 2, 29,
            0, 0, 0));
    private DateTime datetime2 = new DateTime(LocalDateTime.of(2020, 2, 29,
            0, 0, 40));
    private DateTime datetime3 = new DateTime(LocalDateTime.of(2020, 2, 29,
            0, 1, 0));
    private DateTime datetime4 = new DateTime(LocalDateTime.of(2020, 2, 29,
            0, 1, 1));
    private DateTime datetime5 = new DateTime(LocalDateTime.of(2019, 2, 23,
            0, 1, 1));

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void isClose() {
        assertEquals(datetime1.isClose(datetime1), true);
        assertEquals(datetime1.isClose(datetime2), true);
        assertNotEquals(datetime1.isClose(datetime3), true);
        assertNotEquals(datetime1.isClose(datetime4), true);
    }

    @Test
    public void isPast() {
        assertNotEquals(datetime1.isPast(datetime1), true);
        assertEquals(datetime1.isPast(datetime4), true);
        assertEquals(datetime5.isPast(datetime1), true);

    }
}
