package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_MEETING;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ScheduledEventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    // equality criteria testing for two Event objects
    public void equals() {
        // same values -> returns true
        Event doctorApptCopy = new ScheduledEventBuilder(DOCTORAPPT).build();
        assertTrue(DOCTORAPPT.equals(doctorApptCopy));

        // same object -> returns true
        assertTrue(DOCTORAPPT.equals(DOCTORAPPT));

        // null -> returns false
        assertFalse(DOCTORAPPT.equals(null));

        // different type -> returns false
        assertFalse(DOCTORAPPT.equals(5));

        // different person -> returns false
        assertFalse(DOCTORAPPT.equals(MEETING));

        // different name -> returns false
        Event editedDoctorAppt = new ScheduledEventBuilder(DOCTORAPPT).withEventName(VALID_EVENT_NAME_MEETING).build();
        assertFalse(DOCTORAPPT.equals(editedDoctorAppt));

        // different event description -> returns false
        editedDoctorAppt = new ScheduledEventBuilder(DOCTORAPPT).withEventDescription(VALID_EVENT_DESC_MEETING).build();
        assertFalse(DOCTORAPPT.equals(editedDoctorAppt));

        // different date -> returns false
        editedDoctorAppt = new ScheduledEventBuilder(DOCTORAPPT).withEventDate(VALID_EVENT_DATE_MEETING).build();
        assertFalse(DOCTORAPPT.equals(editedDoctorAppt));

        // different time -> returns false
        editedDoctorAppt = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime(VALID_EVENT_START_TIME_MEETING).build();
        assertFalse(DOCTORAPPT.equals(editedDoctorAppt));

        // different address -> returns false
        editedDoctorAppt = new ScheduledEventBuilder(DOCTORAPPT).withEventAddress(VALID_EVENT_ADDRESS_MEETING).build();
        assertFalse(DOCTORAPPT.equals(editedDoctorAppt));
    }

    @Test
    // checking for clashes between two Events
    public void isClashingEvent() {

        // same dates
        Event event = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1200")
                .withEventEndTime("1400")
                .build();

        // null
        Assert.assertThrows(NullPointerException.class, () -> event.isClashingEvent(null));

        // before event, no clash
        Event earlierEventNoClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1000")
                .withEventEndTime("1159")
                .build();
        assertFalse(event.isClashingEvent(earlierEventNoClash));

        Event anotherEarlierEventNoClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1000")
                .withEventEndTime("1200")
                .build();
        assertFalse(event.isClashingEvent(anotherEarlierEventNoClash));

        // after event, no clash
        Event laterEventNoClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1401")
                .withEventEndTime("1500")
                .build();
        assertFalse(event.isClashingEvent(laterEventNoClash));

        Event anotherLaterEventNoClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1400")
                .withEventEndTime("1500")
                .build();
        assertFalse(event.isClashingEvent(anotherLaterEventNoClash));

        // starts before event starts and ends after event starts, with clash
        Event firstEventWithClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1100")
                .withEventEndTime("1300")
                .build();
        assertTrue(event.isClashingEvent(firstEventWithClash));

        // starts after event starts and ends before event ends, with clash
        Event secondEventWithClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1230")
                .withEventEndTime("1330")
                .build();
        assertTrue(event.isClashingEvent(secondEventWithClash));

        // starts before event ends, ends after event ends, clash
        Event thirdEventWithClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1300")
                .withEventEndTime("1500")
                .build();
        assertTrue(event.isClashingEvent(thirdEventWithClash));

        // covers whole duration of event and more, clash
        Event fourthEventWithClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1100")
                .withEventEndTime("1500")
                .build();
        assertTrue(event.isClashingEvent(fourthEventWithClash));

        // same duration as event, clash
        Event fifthEventWithClash = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime("1200")
                .withEventEndTime("1400")
                .build();
        assertTrue(event.isClashingEvent(fifthEventWithClash));

        // different dates
        Event newEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventDate("2018-09-01")
                .withEventStartTime("1200")
                .withEventEndTime("1400")
                .build();

        // different date, same time, no clash
        Event differentDateSameTime = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventDate("2018-09-02")
                .withEventStartTime("1200")
                .withEventEndTime("1400")
                .build();
        assertFalse(event.isClashingEvent(differentDateSameTime));

        // different date, different time, no clash
        Event differentDateDifferentTime = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventDate("2018-09-02")
                .withEventStartTime("1000")
                .withEventEndTime("1200")
                .build();
        assertFalse(event.isClashingEvent(differentDateDifferentTime));
    }
}
