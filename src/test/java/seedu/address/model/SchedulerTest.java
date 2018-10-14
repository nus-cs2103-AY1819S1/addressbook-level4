package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_2_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_3_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.SchedulerBuilder;

public class SchedulerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Scheduler scheduler = new Scheduler();
    private final Scheduler schedulerWithJanuary1and2 = new SchedulerBuilder().withEvent(JANUARY_1_2018_SINGLE)
            .withEvent(JANUARY_2_2018_SINGLE).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), scheduler.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduler.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyScheduler_replacesData() {
        Scheduler newData = getTypicalScheduler();
        scheduler.resetData(newData);
        assertEquals(newData, scheduler);
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduler.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInScheduler_returnsFalse() {
        assertFalse(scheduler.hasEvent(JANUARY_3_2018_SINGLE));
    }

    @Test
    public void hasEvent_eventInScheduler_returnsTrue() {
        scheduler.addEvent(JANUARY_3_2018_SINGLE);
        assertTrue(scheduler.hasEvent(JANUARY_3_2018_SINGLE));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        scheduler.getEventList().remove(0);
    }

    @Test
    public void removeTagNonExistentTagSchedulerUnchanged() throws Exception {
        schedulerWithJanuary1and2.removeTag(new Tag(VALID_TAG_UNUSED));
        Scheduler expectedScheduler = new SchedulerBuilder().withEvent(JANUARY_1_2018_SINGLE)
                .withEvent(JANUARY_2_2018_SINGLE).build();
        assertEquals(expectedScheduler, schedulerWithJanuary1and2);
    }

    @Test
    public void removeTagTagUsedByMultipleEventsTagRemoved() {
        schedulerWithJanuary1and2.removeTag(new Tag(VALID_TAG_PLAY));
        Event january1WithoutPlayTag = new EventBuilder(JANUARY_1_2018_SINGLE).withTags("home").build();
        Event january2WithoutPlayTag = new EventBuilder(JANUARY_2_2018_SINGLE).withTags().build();
        Scheduler expectedScheduler = new SchedulerBuilder().withEvent(january1WithoutPlayTag)
                .withEvent(january2WithoutPlayTag).build();
        assertEquals(expectedScheduler, schedulerWithJanuary1and2);
    }

    /**
     * A stub ReadOnlyScheduler whose events list can violate interface constraints.
     */
    private static class SchedulerStub implements ReadOnlyScheduler {
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        SchedulerStub(Collection<Event> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }

}
