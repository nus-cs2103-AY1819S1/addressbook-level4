package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateUtilTest {

    @Test
    public void isValidDateFormat() {
        // valid date string format
        assertTrue(DateUtil.isValidDateFormat("2018-10-23T17:15:29"));

        // invalid date string formats
        assertFalse(DateUtil.isValidDateFormat("")); // empty date string
        assertFalse(DateUtil.isValidDateFormat("2018-10-23 17:15:29")); // wrongly formatted date string

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isValidDateFormat(null));
    }

    @Test
    public void convertToDate() {
        assertEquals(LocalDateTime.of(2018, 10, 23, 17, 15, 29), DateUtil.convertToDate("2018-10-23T17:15:29"));
        Assert.assertThrows(AssertionError.class, () -> DateUtil.convertToDate("2018-10-23 17:15:29"));
    }

    @Test
    public void convertToString() {
        assertEquals("2018-10-23T17:15:29", DateUtil.convertToString(LocalDateTime
                .of(2018, 10, 23, 17, 15, 29)));
        assertEquals("2019-11-13T00:58:45", DateUtil.convertToString(LocalDateTime
                .of(2019, 11, 13, 0, 58, 45)));
    }
}
