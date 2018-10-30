package seedu.scheduler.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.scheduler.testutil.TypicalEvents.AD_HOC_WORK;
import static seedu.scheduler.testutil.TypicalEvents.DISCUSSION_WITH_JACK;
import static seedu.scheduler.testutil.TypicalEvents.INTERVIEW_WITH_JOHN;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.EventBuilder;
import seedu.scheduler.testutil.SchedulerBuilder;

public class SchedulerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Scheduler scheduler = new Scheduler();
    private final Scheduler schedulerWithDiscussionAndInterview = new SchedulerBuilder().withEvent(DISCUSSION_WITH_JACK)
            .withEvent(INTERVIEW_WITH_JOHN).build();

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
        assertFalse(scheduler.hasEvent(AD_HOC_WORK));
    }

    @Test
    public void hasEvent_eventInScheduler_returnsTrue() {
        scheduler.addEvent(AD_HOC_WORK);
        assertTrue(scheduler.hasEvent(AD_HOC_WORK));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        scheduler.getEventList().remove(0);
    }

    @Test
    public void removeTagNonExistentTagSchedulerUnchanged() throws Exception {
        schedulerWithDiscussionAndInterview.removeTag(new Tag(VALID_TAG_UNUSED));
        Scheduler expectedScheduler = new SchedulerBuilder().withEvent(DISCUSSION_WITH_JACK)
                .withEvent(INTERVIEW_WITH_JOHN).build();
        assertEquals(expectedScheduler, schedulerWithDiscussionAndInterview);
    }

    @Test
    public void removeTagTagUsedByMultipleEventsTagRemoved() {
        schedulerWithDiscussionAndInterview.removeTag(new Tag("Work"));
        Event discussionWithoutWorkTag = new EventBuilder(DISCUSSION_WITH_JACK)
                .withTags("Talk", "Personal").build();
        Event interviewWithoutWorkTag = new EventBuilder(INTERVIEW_WITH_JOHN).withTags("Interview").build();
        Scheduler expectedScheduler = new SchedulerBuilder().withEvent(discussionWithoutWorkTag)
                .withEvent(interviewWithoutWorkTag).build();
        assertEquals(expectedScheduler, schedulerWithDiscussionAndInterview);
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

        @Override
        public Event getFirstInstanceOfEvent(Predicate<Event> predicate) {
            return events.filtered(predicate).get(0);
        }
    }

}
