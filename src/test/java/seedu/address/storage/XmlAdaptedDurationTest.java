package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class XmlAdaptedDurationTest {
    private static final String VALID_START_DATE_STR = "01-01-1990";
    private static final String VALID_END_DATE_STR = "02-01-1990";
    private static final int VALID_DURATION_IN_DAYS = 2;

    private static final String INVALID_START_DATE_STR = "";
    private static final String INVALID_END_DATE_STR = "";
    private static final int INVALID_DURATION_IN_DAYS = -1;

    private XmlAdaptedDuration xad;

    @Before
    public void setup() throws IllegalValueException {
        xad = new XmlAdaptedDuration(VALID_START_DATE_STR, VALID_END_DATE_STR, VALID_DURATION_IN_DAYS);
    }


    @Test(expected = IllegalValueException.class)
    public void constructor_negativeDurationInDays_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDuration(VALID_START_DATE_STR, VALID_END_DATE_STR, INVALID_DURATION_IN_DAYS);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_invalidStartDateStr_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDuration(INVALID_START_DATE_STR, VALID_END_DATE_STR, VALID_DURATION_IN_DAYS);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_invalidEndDateStr_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDuration(VALID_START_DATE_STR, INVALID_END_DATE_STR, VALID_DURATION_IN_DAYS);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_endDateBeforeStartDate_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedDuration(VALID_END_DATE_STR, VALID_START_DATE_STR, VALID_DURATION_IN_DAYS);
    }

    @Test(expected = IllegalValueException.class)
    public void constructor_dateRangeDoesNotMatchDurationInDays_throwsIllegalValueException()
        throws IllegalValueException {
        new XmlAdaptedDuration(VALID_START_DATE_STR, VALID_END_DATE_STR, VALID_DURATION_IN_DAYS + 1);
    }

    @Test
    public void equals_itselfAndItself_returnsTrue() throws IllegalValueException {
        assertTrue(xad.equals(xad));
    }

    @Test
    public void equals_differentType_returnsFalse() throws IllegalValueException {
        assertFalse(xad.equals(1));
    }

    @Test
    public void equals_originalAndCopyOfModelType_returnsTrue() throws IllegalValueException {
        XmlAdaptedDuration anotherXmlAdaptedDuration = new XmlAdaptedDuration(
            xad.toModelType());
        assertTrue(xad.equals(anotherXmlAdaptedDuration));
    }

    @Test
    public void equals_constructedUsingDifferentValues_returnsFalse() throws IllegalValueException {
        XmlAdaptedDuration anotherXmlAdaptedDuration = new XmlAdaptedDuration("02-01-1990", "03-01-1990", 2);
        assertFalse(xad.equals(anotherXmlAdaptedDuration));
    }
}
