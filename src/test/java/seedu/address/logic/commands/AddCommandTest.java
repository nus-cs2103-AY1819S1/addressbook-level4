package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullVolunteer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_volunteerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVolunteerAdded modelStub = new ModelStubAcceptingVolunteerAdded();
        Volunteer validVolunteer = new VolunteerBuilder().build();

        CommandResult commandResult = new AddCommand(validVolunteer).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validVolunteer), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validVolunteer), modelStub.volunteersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateVolunteer_throwsCommandException() throws Exception {
        Volunteer validVolunteer = new VolunteerBuilder().build();
        AddCommand addVolunteerCommand = new AddCommand(validVolunteer);
        ModelStub modelStub = new ModelStubWithVolunteer(validVolunteer);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_VOLUNTEER);
        addVolunteerCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Volunteer alice = new VolunteerBuilder().withName("Alice").build();
        Volunteer bob = new VolunteerBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Context Methods =============================================================
        @Override
        public void setCurrentContext(String contextId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchToRecordContext() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getContextId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getContextName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedEvent(Event selectedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getSelectedEvent() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Volunteer Methods =============================================================
        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVolunteer(Volunteer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateVolunteer(Volunteer target, Volunteer editedVolunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> getFilteredVolunteerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Event Methods =============================================================
        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
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

        //=========== Record Methods =============================================================
        @Override
        public boolean hasRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deleteRecord(Record target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecord(Record target, Record editedRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        //=========== Undo/Redo Methods =============================================================
        @Override
        public void resetStatePointer() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single volunteer.
     */
    private class ModelStubWithVolunteer extends ModelStub {
        private final Volunteer volunteer;

        ModelStubWithVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            this.volunteer = volunteer;
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return this.volunteer.isSameVolunteer(volunteer);
        }
    }

    /**
     * A Model stub that always accept the volunteer being added.
     */
    private class ModelStubAcceptingVolunteerAdded extends ModelStub {
        final ArrayList<Volunteer> volunteersAdded = new ArrayList<>();

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return volunteersAdded.stream().anyMatch(volunteer::isSameVolunteer);
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            volunteersAdded.add(volunteer);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
