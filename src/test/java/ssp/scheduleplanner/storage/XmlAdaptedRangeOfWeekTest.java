package ssp.scheduleplanner.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ssp.scheduleplanner.commons.exceptions.IllegalValueException;
import ssp.scheduleplanner.model.rangeofweek.RangeOfWeek;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.testutil.Assert;

public class XmlAdaptedRangeOfWeekTest {
    private static final String VALID_START_OF_WEEK = "130818";
    private static final String VALID_END_OF_WEEK = "190818";
    private static final String VALID_DESCRIPTION = "Week 1";

    private static final String INVALID_START_OF_WEEK = "320818";
    private static final String INVALID_END_OF_WEEK = "320818";

    private static RangeOfWeek rangeOfWeek = new RangeOfWeek(VALID_START_OF_WEEK, VALID_END_OF_WEEK, VALID_DESCRIPTION);

    @Test
    public void toModelType_validRangeOfWeekDetails_returnsRangeOfWeek() throws Exception {
        XmlAdaptedRangeOfWeek range = new XmlAdaptedRangeOfWeek(rangeOfWeek);
        assertEquals(rangeOfWeek, range.toModelType());
    }

    @Test
    public void toModelType_invalidStartOfWeek_throwsIllegalValueException() {
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        XmlAdaptedRangeOfWeek range = new XmlAdaptedRangeOfWeek(new RangeOfWeek(INVALID_START_OF_WEEK,
                VALID_END_OF_WEEK, VALID_DESCRIPTION));
        Assert.assertThrows(IllegalValueException.class, expectedMessage, range::toModelType);
    }

    @Test
    public void toModelType_invalidEndOfWeek_throwsIllegalValueException() {
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        XmlAdaptedRangeOfWeek range = new XmlAdaptedRangeOfWeek(new RangeOfWeek(VALID_START_OF_WEEK,
                INVALID_END_OF_WEEK, VALID_DESCRIPTION));
        Assert.assertThrows(IllegalValueException.class, expectedMessage, range::toModelType);
    }


}
