package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.AddEventCommandParser.MESSAGE_INVALID_START_END_TIME;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;

import java.time.Duration;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_EVENT_NAME = "Cl@ss";
    private static final String INVALID_EVENT_DESCRIPTION = "+invalid_desc";
    private static final String INVALID_EVENT_DATE = "123123";
    private static final String INVALID_EVENT_START_TIME = "123123";
    private static final String INVALID_EVENT_END_TIME =
            DOCTORAPPT.getEventStartTime().eventTime
                    .minus(Duration.ofHours(1))
                    .toString()
                    .replace(":", "");
    private static final String INVALID_EVENT_ADDRESS = " ";

    private static final String VALID_EVENT_NAME = DOCTORAPPT.getEventName().toString();
    private static final String VALID_EVENT_DESCRIPTION = DOCTORAPPT.getEventDescription().toString();
    private static final String VALID_EVENT_DATE = DOCTORAPPT.getEventDate().toString();
    private static final String VALID_EVENT_START_TIME = DOCTORAPPT.getEventStartTime().toString();
    private static final String VALID_EVENT_END_TIME = DOCTORAPPT.getEventEndTime().toString();
    private static final String VALID_EVENT_ADDRESS = DOCTORAPPT.getEventAddress().toString();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(DOCTORAPPT);
        assertEquals(DOCTORAPPT, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = EventName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(null, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, INVALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = EventDescription.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, null, VALID_EVENT_DATE, VALID_EVENT_START_TIME,
                        VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDescription.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, INVALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = EventDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, null, VALID_EVENT_START_TIME,
                        VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        INVALID_EVENT_START_TIME, VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = EventTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, INVALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(String.format(MESSAGE_INVALID_START_END_TIME, VALID_EVENT_START_TIME,
                INVALID_EVENT_END_TIME));
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE, null,
                        VALID_EVENT_END_TIME, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start" + EventTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, null, VALID_EVENT_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "End" + EventTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, INVALID_EVENT_ADDRESS);
        String expectedMessage = EventAddress.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_DATE,
                        VALID_EVENT_START_TIME, VALID_EVENT_END_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventAddress.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
