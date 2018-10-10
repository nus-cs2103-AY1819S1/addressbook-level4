package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_30_2018_DAILY;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.EventNotFoundException;

public class EventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventList eventList = new EventList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.contains(null);
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(eventList.contains(JANUARY_1_2018_SINGLE));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        eventList.add(JANUARY_1_2018_SINGLE);
        assertTrue(eventList.contains(JANUARY_1_2018_SINGLE));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.add(null);
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvent(null, JANUARY_1_2018_SINGLE);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvent(JANUARY_1_2018_SINGLE, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        eventList.setEvent(JANUARY_1_2018_SINGLE, JANUARY_1_2018_SINGLE);
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        eventList.add(JANUARY_1_2018_SINGLE);
        eventList.setEvent(JANUARY_1_2018_SINGLE, JANUARY_1_2018_SINGLE);
        EventList expectedEventList = new EventList();
        expectedEventList.add(JANUARY_1_2018_SINGLE);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        eventList.add(JANUARY_1_2018_SINGLE);
        eventList.setEvent(JANUARY_1_2018_SINGLE, JANUARY_30_2018_DAILY);
        EventList expectedEventList = new EventList();
        expectedEventList.add(JANUARY_30_2018_DAILY);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        eventList.remove(JANUARY_1_2018_SINGLE);
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        eventList.add(JANUARY_1_2018_SINGLE);
        eventList.remove(JANUARY_1_2018_SINGLE);
        EventList expectedEventList = new EventList();
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvents_nullEventList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvents((EventList) null);
    }

    @Test
    public void setEvents_eventList_replacesOwnListWithProvidedEventList() {
        eventList.add(JANUARY_1_2018_SINGLE);
        EventList expectedEventList = new EventList();
        expectedEventList.add(JANUARY_30_2018_DAILY);
        eventList.setEvents(expectedEventList);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvents((List<Event>) null);
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        eventList.add(JANUARY_1_2018_SINGLE);
        List<Event> otherEventList = Collections.singletonList(JANUARY_30_2018_DAILY);
        eventList.setEvents(otherEventList);
        EventList expectedEventList = new EventList();
        expectedEventList.add(JANUARY_30_2018_DAILY);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventList.asUnmodifiableObservableList().remove(0);
    }
}
