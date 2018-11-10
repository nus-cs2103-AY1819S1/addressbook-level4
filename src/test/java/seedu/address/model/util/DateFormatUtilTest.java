package seedu.address.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.util.DateFormatUtil.isValidDateFormat;
import static seedu.address.model.util.DateFormatUtil.parseDate;

import java.util.Date;

import org.junit.Test;

public class DateFormatUtilTest {

    @Test
    public void parseDate_returnsNull() {
        assertEquals(parseDate(null), null);
    }

    @Test
    public void parseDate_invalidDateFormat() {
        assertEquals(parseDate("12.12.18"), null);
    }

    @Test
    public void parseDate_validDate() {
        // Comparing parsed date against intended date in unix timestamp
        assertEquals(parseDate("12-12-2018"), new Date(1544544000000L));
    }


    @Test
    public void isValidDateFormat_validDateFormat_minimalFormat() {
        assertTrue(isValidDateFormat("12-12-18"));
        assertTrue(isValidDateFormat("13-12-2018"));
    }

    @Test
    public void isValidDateFormat_validDateFormat_standardFormat() {
        assertTrue(isValidDateFormat("12-12-18 1200"));
        assertTrue(isValidDateFormat("13-12-2018 1200"));
    }

    @Test
    public void isValidDateFormat_invalidDateFormat_allFormats() {
        assertFalse(isValidDateFormat("12-13-18"));
        assertFalse(isValidDateFormat("32-12-2018 1200"));

        assertFalse(isValidDateFormat("12-12-18 100"));
        assertFalse(isValidDateFormat("12-12-018 2100"));

    }
}
