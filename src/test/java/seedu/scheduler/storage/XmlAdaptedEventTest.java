package seedu.scheduler.storage;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_EVENT_NAME = " study ";
    private static final String INVALID_TAG = "#friend";

    private static final UUID VALID_UUID = JANUARY_1_2018_SINGLE.getUuid();
    private static final String VALID_EVENT_NAME = JANUARY_1_2018_SINGLE.getEventName().toString();
    private static final DateTime VALID_START_DATETIME = JANUARY_1_2018_SINGLE.getStartDateTime();
    private static final DateTime VALID_END_DATETIME = JANUARY_1_2018_SINGLE.getEndDateTime();
    private static final String VALID_DESCRIPTION = JANUARY_1_2018_SINGLE.getDescription().toString();
    private static final String VALID_VENUE = JANUARY_1_2018_SINGLE.getVenue().toString();
    private static final RepeatType VALID_REPEAT_TYPE = JANUARY_1_2018_SINGLE.getRepeatType();
    private static final DateTime VALID_REPEAT_UNTIL_DATETIME = JANUARY_1_2018_SINGLE.getRepeatUntilDateTime();
    private static final List<XmlAdaptedTag> VALID_TAGS = JANUARY_1_2018_SINGLE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(JANUARY_1_2018_SINGLE);
        assertEquals(JANUARY_1_2018_SINGLE, event.toModelType());
    }

    @Test
    public void toModelType_nullUuid_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_EVENT_NAME, VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_UUID, INVALID_EVENT_NAME, VALID_START_DATETIME, VALID_END_DATETIME,
                        VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                        VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        String expectedMessage = EventName.MESSAGE_EVENT_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, null, VALID_START_DATETIME, VALID_END_DATETIME,
                VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, null, VALID_END_DATETIME,
                VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME, null,
                VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME,
                VALID_END_DATETIME, null, VALID_VENUE, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME,
                VALID_END_DATETIME, VALID_DESCRIPTION, null, VALID_REPEAT_TYPE,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullRepeatType_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME,
                VALID_END_DATETIME, VALID_DESCRIPTION, VALID_VENUE, null,
                VALID_REPEAT_UNTIL_DATETIME, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullRepeatUntilDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME,
                VALID_END_DATETIME, VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                null, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_UUID, VALID_EVENT_NAME, VALID_START_DATETIME, VALID_END_DATETIME,
                        VALID_DESCRIPTION, VALID_VENUE, VALID_REPEAT_TYPE,
                        VALID_REPEAT_UNTIL_DATETIME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

}
