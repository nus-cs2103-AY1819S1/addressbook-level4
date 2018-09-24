package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.YOUTH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Location;
import seedu.address.model.event.Name;
import seedu.address.model.event.Time;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = " Bl@@d";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_START_DATE = "123";
    private static final String INVALID_END_DATE = "456";
    private static final String INVALID_START_TIME = "789";
    private static final String INVALID_END_TIME = "555";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = YOUTH.getName().toString();
    private static final String VALID_LOCATION = YOUTH.getLocation().toString();
    private static final String VALID_START_DATE = YOUTH.getStartDate().toString();
    private static final String VALID_END_DATE = YOUTH.getEndDate().toString();
    private static final String VALID_START_TIME = YOUTH.getStartTime().toString();
    private static final String VALID_END_TIME = YOUTH.getEndTime().toString();
    private static final String VALID_DESCRIPTION = YOUTH.getDescription().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = YOUTH.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(YOUTH);
        assertEquals(YOUTH, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, INVALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_START_DATE, VALID_END_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, INVALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, null, VALID_END_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, INVALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, null,
                VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        INVALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                null, VALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, INVALID_END_TIME, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                VALID_START_TIME, null, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                VALID_START_TIME, VALID_END_TIME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_LOCATION, VALID_START_DATE, VALID_END_DATE,
                        VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }
}
