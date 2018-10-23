package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CLASHING_EVENT_END_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.CLASHING_EVENT_START_TIME_DOCTORAPPT;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventClashException;
import seedu.address.testutil.ScheduledEventBuilder;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.contains(null);
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(DOCTORAPPT));
    }

    @Test
    public void contains_clashingEventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.containsClashingEvent(DOCTORAPPT));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(DOCTORAPPT);
        assertTrue(uniqueEventList.contains(DOCTORAPPT));
    }

    @Test
    public void contains_clashingEventInList_returnsTrue() {
        uniqueEventList.add(DOCTORAPPT);
        Event clashingEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime(CLASHING_EVENT_START_TIME_DOCTORAPPT)
                .withEventEndTime(CLASHING_EVENT_END_TIME_DOCTORAPPT)
                .build();
        assertTrue(uniqueEventList.containsClashingEvent(clashingEvent));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.add(null);
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(DOCTORAPPT);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(DOCTORAPPT);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((UniqueEventList) null);
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(DOCTORAPPT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(DOCTORAPPT);
        List<Event> eventList = Collections.singletonList(MEETING);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(DOCTORAPPT, DOCTORAPPT);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvents(listWithDuplicateEvents);
    }

    @Test
    public void setEvents_listWithClashingEvents_throwsDuplicateEventException() {
        Event clashingEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime(CLASHING_EVENT_START_TIME_DOCTORAPPT)
                .withEventEndTime(CLASHING_EVENT_END_TIME_DOCTORAPPT)
                .build();
        List<Event> listWithClashingEvents = Arrays.asList(DOCTORAPPT, clashingEvent);
        thrown.expect(EventClashException.class);
        uniqueEventList.setEvents(listWithClashingEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().add(DOCTORAPPT);
    }
}
