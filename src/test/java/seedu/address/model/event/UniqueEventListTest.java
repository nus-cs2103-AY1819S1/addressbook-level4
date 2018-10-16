package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;
import static seedu.address.testutil.TypicalEvents.MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;

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
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(DOCTORAPPT);
        assertTrue(uniqueEventList.contains(DOCTORAPPT));
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
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().add(DOCTORAPPT);
    }
}
