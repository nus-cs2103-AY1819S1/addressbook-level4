package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.PopUpManager;
import seedu.scheduler.model.ReadOnlyScheduler;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.storage.Storage;
import seedu.scheduler.testutil.EventBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new AddCommand(validEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEvent.getEventName()),
                commandResult.feedbackToUser);
        assertEquals(List.of(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        AddCommand addCommand = new AddCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_excessiveEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubAllowAddEventsAttempt();
        Event validEvent = new EventBuilder().withStartDateTime(LocalDateTime.now())
                .withEndDateTime(LocalDateTime.now().plusHours(1))
                .withRepeatType(RepeatType.DAILY)
                .withRepeatUntilDateTime(LocalDateTime.now().plusYears(1)).build();
        AddCommand addCommand = new AddCommand(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_OVERFLOW_EVENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event study = new EventBuilder().withEventName("study").build();
        Event play = new EventBuilder().withEventName("play").build();
        AddCommand addStudyCommand = new AddCommand(study);
        AddCommand addPlayCommand = new AddCommand(play);

        // same object -> equals
        assertEquals(addStudyCommand, addStudyCommand);

        // same values -> equals
        AddCommand addStudyCommandCopy = new AddCommand(study);
        assertEquals(addStudyCommand, addStudyCommandCopy);

        // different types -> not equals
        assertNotEquals(1, addStudyCommand);

        // null -> not equals
        assertNotEquals(null, addStudyCommand);

        // different event -> not equals
        assertNotEquals(addStudyCommand, addPlayCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyScheduler newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRepeatingEvents(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteUpcomingEvents(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvents(List<Event> events) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRepeatingEvents(Event target, List<Event> editedEvents) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateUpcomingEvents(Event target, List<Event> editedEvents) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getFirstInstanceOfEvent(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitScheduler() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void syncWithPopUpManager(PopUpManager popUpManager, Storage storage) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * An empty Model stub that allow attempts to add events to event list.
     */
    private class ModelStubAllowAddEventsAttempt extends ModelStub {
        private final EventList eventList = new EventList();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return false;
        }

        @Override
        public void addEvents(List<Event> eventsToAdd) {
            requireAllNonNull(eventsToAdd);
            eventList.addEvents(eventsToAdd);
        }
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accepts the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvents(List<Event> events) {
            requireNonNull(events);
            eventsAdded.addAll(events);
        }

        @Override
        public void commitScheduler() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyScheduler getScheduler() {
            return new Scheduler();
        }
    }

}
