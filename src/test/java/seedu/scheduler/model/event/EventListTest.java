package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.testutil.TypicalEvents.DISCUSSION_WITH_JACK;
import static seedu.scheduler.testutil.TypicalEvents.INTERVIEW_WITH_JOHN;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAILY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_THREE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JILL_DAILY_LIST;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.model.event.exceptions.EventNotFoundException;
import seedu.scheduler.model.event.exceptions.EventOverflowException;
import seedu.scheduler.testutil.EventBuilder;

public class EventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventList eventList = new EventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.contains(null);
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(eventList.contains(DISCUSSION_WITH_JACK));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        eventList.add(DISCUSSION_WITH_JACK);
        assertTrue(eventList.contains(DISCUSSION_WITH_JACK));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.add(null);
    }

    @Test
    public void add_excessiveEvent_throwsEventOverflowException() {
        thrown.expect(EventOverflowException.class);
        Event excessiveEvent = new EventBuilder(JIM_BIRTHDAY_YEAR_ONE).withRepeatType(RepeatType.DAILY).build();
        eventList.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(excessiveEvent));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvent(null, DISCUSSION_WITH_JACK);
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvent(DISCUSSION_WITH_JACK, null);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        eventList.setEvent(DISCUSSION_WITH_JACK, DISCUSSION_WITH_JACK);
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        eventList.add(DISCUSSION_WITH_JACK);
        eventList.setEvent(DISCUSSION_WITH_JACK, DISCUSSION_WITH_JACK);
        EventList expectedEventList = new EventList();
        expectedEventList.add(DISCUSSION_WITH_JACK);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        eventList.add(DISCUSSION_WITH_JACK);
        eventList.setEvent(DISCUSSION_WITH_JACK, INTERVIEW_WITH_JOHN);
        EventList expectedEventList = new EventList();
        expectedEventList.add(INTERVIEW_WITH_JOHN);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvents_nullEditedListOfEventWithPredicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvents(DISCUSSION_WITH_JACK, null,
            event -> event.getEventSetUid().equals(DISCUSSION_WITH_JACK.getEventSetUid()));
    }

    @Test
    public void setEvents_nullPredicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvents(DISCUSSION_WITH_JACK, List.of(INTERVIEW_WITH_JOHN), null);
    }

    @Test
    public void setEvents_editedListSmallerThanTargetListWithPredicate_success() {
        eventList.addEvents(STUDY_WITH_JANE_DAILY_LIST);
        List<Event> editedList = STUDY_WITH_JILL_DAILY_LIST.subList(0, STUDY_WITH_JILL_DAILY_LIST.size() - 1);
        eventList.setEvents(STUDY_WITH_JANE_DAY_THREE, editedList,
            event -> event.getEventSetUid().equals(STUDY_WITH_JANE_DAY_THREE.getEventSetUid()));
        EventList expectedEventList = new EventList();
        expectedEventList.addEvents(editedList);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvents_editedListLargerThanTargetListWithPredicate_success() {
        eventList.addEvents(STUDY_WITH_JANE_DAILY_LIST.subList(0, STUDY_WITH_JANE_DAILY_LIST.size() - 1));
        eventList.setEvents(STUDY_WITH_JANE_DAY_THREE, STUDY_WITH_JILL_DAILY_LIST,
            event -> event.getEventSetUid().equals(STUDY_WITH_JANE_DAY_THREE.getEventSetUid()));
        EventList expectedEventList = new EventList();
        expectedEventList.addEvents(STUDY_WITH_JILL_DAILY_LIST);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void setEvents_anyNullEditedListOfEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.setEvents((List.of(DISCUSSION_WITH_JACK, null, INTERVIEW_WITH_JOHN)));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.remove(null);
    }

    @Test
    public void remove_eventDoesNotExistWithoutPredicate_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        eventList.remove(DISCUSSION_WITH_JACK);
    }

    @Test
    public void remove_existingEventWithoutPredicate_removesEvent() {
        eventList.add(DISCUSSION_WITH_JACK);
        eventList.remove(DISCUSSION_WITH_JACK);
        EventList expectedEventList = new EventList();
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void remove_eventDoesNotExistWithPredicate_throwsEventNotFoundException() {
        thrown.expect(EventNotFoundException.class);
        eventList.remove(DISCUSSION_WITH_JACK,
            event -> event.getEventSetUid().equals(DISCUSSION_WITH_JACK.getEventSetUid()));
    }

    @Test
    public void remove_existingEventsWithPredicate_removesEvents() {
        eventList.addEvents(STUDY_WITH_JANE_DAILY_LIST);
        eventList.remove(STUDY_WITH_JANE_DAY_ONE,
            event -> event.getEventSetUid().equals(STUDY_WITH_JANE_DAY_ONE.getEventSetUid()));
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
        eventList.add(DISCUSSION_WITH_JACK);
        EventList expectedEventList = new EventList();
        expectedEventList.add(INTERVIEW_WITH_JOHN);
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
        eventList.add(DISCUSSION_WITH_JACK);
        List<Event> otherEventList = Collections.singletonList(INTERVIEW_WITH_JOHN);
        eventList.setEvents(otherEventList);
        EventList expectedEventList = new EventList();
        expectedEventList.add(INTERVIEW_WITH_JOHN);
        assertEquals(expectedEventList, eventList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventList.asUnmodifiableObservableList().remove(0);
    }
}
