package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_UUID_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_2018_MONTHLY;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.scheduler.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void unmodifiableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        event.getTags().remove(0);
    }

    @Test
    public void isValidEventDateTime() {
        DateTime startDateTime = new DateTime(LocalDateTime.of(2018, 1, 1, 1, 1));
        DateTime firstValidEndDateTime = new DateTime(LocalDateTime.of(2018, 1, 1, 1, 1));
        DateTime secondValidEndDateTime = new DateTime(LocalDateTime.of(2018, 1, 1, 1, 2));
        DateTime invalidEndDateTime = new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0));
        assertTrue(Event.isValidEventDateTime(startDateTime, firstValidEndDateTime));
        assertTrue(Event.isValidEventDateTime(startDateTime, secondValidEndDateTime));
        assertFalse(Event.isValidEventDateTime(startDateTime, invalidEndDateTime));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(JANUARY_1_2018_SINGLE.isSameEvent(JANUARY_1_2018_SINGLE));

        // null -> returns false
        assertFalse(JANUARY_1_2018_SINGLE.isSameEvent(null));

        // different uuid -> returns false
        Event editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withUuid(UUID.randomUUID()).build();
        assertFalse(JANUARY_1_2018_SINGLE.isSameEvent(editedJanuaryFirst2018SingleEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event januaryFirst2018SingleCopy = new EventBuilder(JANUARY_1_2018_SINGLE).build();
        assertEquals(JANUARY_1_2018_SINGLE, januaryFirst2018SingleCopy);

        // same object -> returns true
        assertEquals(JANUARY_1_2018_SINGLE, JANUARY_1_2018_SINGLE);

        // null -> returns false
        assertNotEquals(null, JANUARY_1_2018_SINGLE);

        // different type -> returns false
        assertNotEquals(5, JANUARY_1_2018_SINGLE);

        // different event -> returns false
        assertNotEquals(JANUARY_1_2018_SINGLE, JANUARY_1_2018_MONTHLY);

        // different uuid -> return false
        Event editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withUuid(VALID_EVENT_UUID_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different eventName -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different startDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withStartDateTime(VALID_START_DATETIME_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different endDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEndDateTime(VALID_END_DATETIME_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different description -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withDescription(VALID_DESCRIPTION_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different venue -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withVenue(VALID_VENUE_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different repeat type -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withRepeatType(VALID_REPEAT_TYPE_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different repeatUntilDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEndDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);
    }
}
