package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateUtilTest {

    @Test
    public void isValidDateFormat() {
        // valid date string format
        assertTrue(DateUtil.isValidDateFormat("2018-10-23"));

        // invalid date string formats
        assertFalse(DateUtil.isValidDateFormat("")); // empty date string
        assertFalse(DateUtil.isValidDateFormat("201-10-23")); // wrongly formatted date string

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isValidDateFormat(null));
    }

    @Test
    public void convertToDate() {
        assertEquals(LocalDate.of(2018, 10, 23), DateUtil.convertToDate("2018-10-23"));
        Assert.assertThrows(AssertionError.class, () -> DateUtil.convertToDate("201-10-23"));
    }

    @Test
    public void convertToString() {
        assertEquals("2018-10-23", DateUtil.convertToString(LocalDate.of(2018, 10, 23)));
        assertEquals("2019-11-13", DateUtil.convertToString(LocalDate.of(2019, 11, 13)));
    }
}
