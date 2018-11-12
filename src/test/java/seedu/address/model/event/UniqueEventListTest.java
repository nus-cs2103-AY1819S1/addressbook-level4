package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPETITION;
import static seedu.address.testutil.TypicalEvents.BLOOD;
import static seedu.address.testutil.TypicalEvents.YOUTH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

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
        assertFalse(uniqueEventList.contains(BLOOD));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(BLOOD);
        assertTrue(uniqueEventList.contains(BLOOD));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(BLOOD);
        Event editedBlood = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH)
                .withTags(VALID_TAG_COMPETITION).build();
        assertTrue(uniqueEventList.contains(editedBlood));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.add(null);
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(BLOOD);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(BLOOD);
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(null, BLOOD);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvent(BLOOD, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(BLOOD, BLOOD);
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(BLOOD);
        uniqueEventList.setEvent(BLOOD, BLOOD);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BLOOD);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(BLOOD);
        Event editedBlood = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH)
                .withTags(VALID_TAG_COMPETITION).build();
        uniqueEventList.setEvent(BLOOD, editedBlood);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedBlood);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(BLOOD);
        uniqueEventList.setEvent(BLOOD, YOUTH);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(YOUTH);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(BLOOD);
        uniqueEventList.add(YOUTH);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(BLOOD, YOUTH);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.remove(BLOOD);
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(BLOOD);
        uniqueEventList.remove(BLOOD);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((UniqueEventList) null);
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(BLOOD);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(YOUTH);
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
        uniqueEventList.add(BLOOD);
        List<Event> eventList = Collections.singletonList(YOUTH);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(YOUTH);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(BLOOD, BLOOD);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvents(listWithDuplicateEvents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asUnmodifiableObservableList().remove(0);
    }
}
