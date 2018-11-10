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
import static seedu.scheduler.testutil.TypicalEvents.DISCUSSION_WITH_JACK;
import static seedu.scheduler.testutil.TypicalEvents.INTERVIEW_WITH_JOHN;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_ONE;

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
        assertTrue(DISCUSSION_WITH_JACK.isSameEvent(DISCUSSION_WITH_JACK));

        // null -> returns false
        assertFalse(DISCUSSION_WITH_JACK.isSameEvent(null));

        // different uuid -> returns false
        Event editedDiscussionWithJackeEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withEventSetUid(UUID.randomUUID()).build();
        assertFalse(DISCUSSION_WITH_JACK.isSameEvent(editedDiscussionWithJackeEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event discussionWithJackSingleCopy = new EventBuilder(DISCUSSION_WITH_JACK).build();
        assertEquals(DISCUSSION_WITH_JACK, discussionWithJackSingleCopy);

        // same object -> returns true
        assertEquals(DISCUSSION_WITH_JACK, DISCUSSION_WITH_JACK);

        // null -> returns false
        assertNotEquals(null, DISCUSSION_WITH_JACK);

        // different type -> returns false
        assertNotEquals(5, DISCUSSION_WITH_JACK);

        // different event -> returns false
        assertNotEquals(DISCUSSION_WITH_JACK, INTERVIEW_WITH_JOHN);

        // different uuid -> return false
        Event editedDiscussionWithJackEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withEventSetUid(VALID_EVENT_UUID_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different eventName -> returns false
        editedDiscussionWithJackEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different startDateTime -> returns false
        editedDiscussionWithJackEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withStartDateTime(VALID_START_DATETIME_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different endDateTime -> returns false
        editedDiscussionWithJackEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withEndDateTime(VALID_END_DATETIME_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different description -> returns false
        editedDiscussionWithJackEvent =
                new EventBuilder(DISCUSSION_WITH_JACK).withDescription(VALID_DESCRIPTION_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different venue -> returns false
        editedDiscussionWithJackEvent =
                new EventBuilder(DISCUSSION_WITH_JACK).withVenue(VALID_VENUE_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different repeat type -> returns false
        editedDiscussionWithJackEvent =
                new EventBuilder(DISCUSSION_WITH_JACK).withRepeatType(VALID_REPEAT_TYPE_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);

        // different repeatUntilDateTime -> returns false
        editedDiscussionWithJackEvent = new EventBuilder(DISCUSSION_WITH_JACK)
                .withEndDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).build();
        assertNotEquals(DISCUSSION_WITH_JACK, editedDiscussionWithJackEvent);
    }

    @Test
    public void isRepeatEvent() {
        // Event without a RepeatType is not a RepeatEvent
        Event discussionWithJackSingleCopy = new EventBuilder(DISCUSSION_WITH_JACK).build();
        assertFalse(discussionWithJackSingleCopy.isRepeatEvent());

        // Event with a RepeatType is RepeatEvent
        Event studyWithJaneCopy = new EventBuilder(STUDY_WITH_JANE_DAY_ONE).build();
        assertTrue(studyWithJaneCopy.isRepeatEvent());
    }
}
