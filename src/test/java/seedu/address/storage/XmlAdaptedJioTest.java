package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedJio.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Name;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalJios;

public class XmlAdaptedJioTest {
    private static final String INVALID_NAME = "Hw@ngs";
    private static final String INVALID_WEEK = "holiday";
    private static final String INVALID_DAY = "mayday";
    private static final String INVALID_TIME = "2567";
    private static final String INVALID_ADDRESS = " ";

    private static final Jio LUNCH = TypicalJios.getTypicalJios().get(0);
    private static final String VALID_NAME = LUNCH.getName().toString();
    private static final String VALID_WEEK = LUNCH.getDate().getWeek().toString();
    private static final String VALID_DAY = LUNCH.getDate().getDay().toString();
    private static final String VALID_TIME = LUNCH.getDate().getTime().toString();
    private static final String VALID_ADDRESS = LUNCH.getAddress().toString();
    private static final List<XmlAdaptedUsername> VALID_PEOPLE = LUNCH.getPeople().stream()
            .map(XmlAdaptedUsername::new)
            .collect(Collectors.toList());
    private static final XmlAdaptedUsername VALID_CREATOR = new XmlAdaptedUsername("johnnydoe");

    @Test
    public void toModelType_validJioDetails_returnsJio() throws Exception {
        XmlAdaptedJio jio = new XmlAdaptedJio(LUNCH);
        assertEquals(LUNCH, jio.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(INVALID_NAME, VALID_WEEK, VALID_DAY, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(null, VALID_WEEK, VALID_DAY, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_invalidWeek_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, INVALID_WEEK, VALID_DAY, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = Week.MESSAGE_WEEK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_nullWeek_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, null, VALID_DAY, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Week.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, INVALID_DAY, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = Day.MESSAGE_DAY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, null, VALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, VALID_DAY, INVALID_TIME, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, VALID_DAY, null, VALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, VALID_DAY, VALID_TIME, INVALID_ADDRESS,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedJio jio = new XmlAdaptedJio(VALID_NAME, VALID_WEEK, VALID_DAY, VALID_TIME, null,
                VALID_PEOPLE, VALID_CREATOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, jio::toModelType);
    }

}
