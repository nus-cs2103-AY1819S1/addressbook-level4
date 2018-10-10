package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_UUID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE;
import static seedu.address.testutil.TypicalEvents.DAY2018_THREE_DAY_LIST;
import static seedu.address.testutil.TypicalEvents.FEBRUARY_29_2016_YEARLY;
import static seedu.address.testutil.TypicalEvents.FEBRUARY_29_FOUR_YEAR_LIST;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_MONTHLY;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_YEARLY;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_THREE_YEAR_LIST;
import static seedu.address.testutil.TypicalEvents.JANUARY_30_2018_DAILY;
import static seedu.address.testutil.TypicalEvents.JANUARY_31_2018_MONTHLY;
import static seedu.address.testutil.TypicalEvents.MONTH2018_1_THREE_MONTH_LIST;
import static seedu.address.testutil.TypicalEvents.MONTH2018_31_THREE_MONTH_LIST;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void unmodifiableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        Event.generateAllRepeatedEvents(JANUARY_1_2018_SINGLE).remove(0);
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
    public void generateAllRepeatedEvents() {
        // daily event
        assertEquals(DAY2018_THREE_DAY_LIST, Event.generateAllRepeatedEvents(JANUARY_30_2018_DAILY));

        // monthly event
        assertEquals(MONTH2018_1_THREE_MONTH_LIST, Event.generateAllRepeatedEvents(JANUARY_1_2018_MONTHLY));
        assertEquals(MONTH2018_31_THREE_MONTH_LIST, Event.generateAllRepeatedEvents(JANUARY_31_2018_MONTHLY));

        // yearly event
        assertEquals(JANUARY_1_THREE_YEAR_LIST, Event.generateAllRepeatedEvents(JANUARY_1_2018_YEARLY));
        assertEquals(FEBRUARY_29_FOUR_YEAR_LIST, Event.generateAllRepeatedEvents(FEBRUARY_29_2016_YEARLY));
    }

    @Test
    public void generateDailyRepeatEvents() {
        // standard day of a year
        assertEquals(DAY2018_THREE_DAY_LIST, Event.generateDailyRepeatEvents(JANUARY_30_2018_DAILY));
    }

    @Test
    public void generateMonthlyRepeatEvents() {
        // standard day of a year
        assertEquals(MONTH2018_1_THREE_MONTH_LIST, Event.generateMonthlyRepeatEvents(JANUARY_1_2018_MONTHLY));
        // days that do not appear every month
        assertEquals(MONTH2018_31_THREE_MONTH_LIST, Event.generateMonthlyRepeatEvents(JANUARY_31_2018_MONTHLY));
    }

    @Test
    public void generateYearlyRepeatEvents() {
        // standard day of year
        assertEquals(JANUARY_1_THREE_YEAR_LIST, Event.generateYearlyRepeatEvents(JANUARY_1_2018_YEARLY));
        // day of leap year
        assertEquals(FEBRUARY_29_FOUR_YEAR_LIST, Event.generateYearlyRepeatEvents(FEBRUARY_29_2016_YEARLY));
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
                .withUuid(VALID_EVENT_UUID).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different eventName -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different startDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withStartDateTime(VALID_START_DATETIME).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different endDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEndDateTime(VALID_END_DATETIME).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different description -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withDescription(VALID_DESCRIPTION).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different priority -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withPriority(VALID_PRIORITY).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different venue -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withVenue(VALID_VENUE).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different repeat type -> returns false
        editedJanuaryFirst2018SingleEvent =
                new EventBuilder(JANUARY_1_2018_SINGLE).withRepeatType(VALID_REPEAT_TYPE).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);

        // different repeatUntilDateTime -> returns false
        editedJanuaryFirst2018SingleEvent = new EventBuilder(JANUARY_1_2018_SINGLE)
                .withEndDateTime(VALID_REPEAT_UNTIL_DATETIME).build();
        assertNotEquals(JANUARY_1_2018_SINGLE, editedJanuaryFirst2018SingleEvent);
    }
}
