package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidHour() {
        Time time = new Time(23, 17);

        //valid hour
        assertTrue(time.isValidHour(23));

        //invalid hour
        assertFalse(time.isValidHour(-100));
        assertFalse(time.isValidHour(100));
        assertFalse(time.isValidHour(24));
    }

    @Test
    public void isValidMinute() {
        Time time = new Time(23, 17);

        //valid minute
        assertTrue(time.isValidMinute(23));

        //invalid minute
        assertFalse(time.isValidMinute(-100));
        assertFalse(time.isValidMinute(100));
        assertFalse(time.isValidMinute(9022324));
    }

    @Test
    public void equals() {
        Time time = new Time(23, 17);
        Time time2 = new Time(23, 17);
        Time time3 = new Time(2, 19);

        //same obj
        assertTrue(time.equals(time));

        //same fields
        assertTrue(time.equals(time2));

        //diff
        assertFalse(time2.equals(time3));
        assertFalse(time2.equals(null));
    }
}
