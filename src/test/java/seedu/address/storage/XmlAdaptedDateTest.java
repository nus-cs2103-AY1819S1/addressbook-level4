package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedDate.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDates.DATE_RECESS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;
import seedu.address.testutil.Assert;

public class XmlAdaptedDateTest {
    private static final String INVALID_WEEK = "one";
    private static final String INVALID_DAY = "monday";
    private static final String INVALID_TIME = "5555";

    private static final String VALID_WEEK = DATE_RECESS.getWeek().toString();
    private static final String VALID_DAY = DATE_RECESS.getDay().toString();
    private static final String VALID_TIME = DATE_RECESS.getTime().toString();

    @Test
    public void toModelType_validDateDetails_returnsDate() throws Exception {
        XmlAdaptedDate date = new XmlAdaptedDate(DATE_RECESS);
        assertEquals(DATE_RECESS, date.toModelType());
    }

    @Test
    public void toModelType_invalidWeek_throwsIllegalValueException() {
        XmlAdaptedDate date =
                new XmlAdaptedDate(INVALID_WEEK, VALID_DAY, VALID_TIME);
        String expectedMessage = Week.MESSAGE_WEEK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullWeek_throwsIllegalValueException() {
        XmlAdaptedDate date = new XmlAdaptedDate(
                null, VALID_DAY, VALID_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Week.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        XmlAdaptedDate date =
                new XmlAdaptedDate(VALID_WEEK, INVALID_DAY, VALID_TIME);
        String expectedMessage = Day.MESSAGE_DAY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        XmlAdaptedDate date = new XmlAdaptedDate(
                VALID_WEEK, null, VALID_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        XmlAdaptedDate date =
                new XmlAdaptedDate(VALID_WEEK, VALID_DAY, INVALID_TIME);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        XmlAdaptedDate date = new XmlAdaptedDate(
                VALID_WEEK, VALID_DAY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

}
