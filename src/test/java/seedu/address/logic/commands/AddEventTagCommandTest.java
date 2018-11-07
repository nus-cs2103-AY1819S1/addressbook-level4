package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.APPOINTMENT_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.filereader.FileReader;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains unit tests for {@code AddEventTagCommand}.
 */
public class AddEventTagCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullEventTag_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventTagCommand(null);
    }

    @Test
    public void execute_eventTagAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventTagAdded modelStub = new ModelStubAcceptingEventTagAdded();
        Set<Tag> validEventTags = new HashSet<>(Arrays.asList(APPOINTMENT_TAG));

        CommandResult commandResult = new AddEventTagCommand(validEventTags).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventTagCommand.MESSAGE_SUCCESS, validEventTags), commandResult.feedbackToUser);
        assertEquals(new ArrayList<>(validEventTags), modelStub.eventTagsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateEventTags_throwsCommandException() throws Exception {
        Set<Tag> validTags = new HashSet<>(Arrays.asList(APPOINTMENT_TAG));
        AddEventTagCommand addEventTagCommand = new AddEventTagCommand(validTags);
        ModelStub modelStub = new ModelStubWithEventTag(APPOINTMENT_TAG);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddEventTagCommand.MESSAGE_DUPLICATE_TAG,
                new HashSet<>(Arrays.asList(APPOINTMENT_TAG))));
        addEventTagCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_emptyEventTags_success() throws Exception {
        ModelStubAcceptingEventTagAdded modelStub = new ModelStubAcceptingEventTagAdded();
        Set<Tag> validEventTags = new HashSet<>();

        CommandResult commandResult = new AddEventTagCommand(validEventTags).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventTagCommand.MESSAGE_SUCCESS, validEventTags), commandResult.feedbackToUser);
        assertEquals(new ArrayList<>(validEventTags), modelStub.eventTagsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Tag appointmentTag = new Tag(VALID_TAG_APPOINTMENT);
        Tag meetingTag = new Tag(VALID_TAG_MEETING);
        AddEventTagCommand addAppointmentTagCommand =
                new AddEventTagCommand(new HashSet<>(Arrays.asList(appointmentTag)));
        AddEventTagCommand addMeetingTagCommand =
                new AddEventTagCommand(new HashSet<>(Arrays.asList(meetingTag)));


        // same object -> returns true
        assertTrue(addAppointmentTagCommand.equals(addAppointmentTagCommand));

        // same values -> returns true
        AddEventTagCommand addAppointmentagCommandCopy =
                new AddEventTagCommand(new HashSet<>(Arrays.asList(appointmentTag)));
        assertTrue(addAppointmentTagCommand.equals(addAppointmentagCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentagCommandCopy.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(addAppointmentagCommandCopy.equals(null));

        // different event -> returns false
        assertFalse(addAppointmentagCommandCopy.equals(addMeetingTagCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void importContacts(FileReader fileReader) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasClashingEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEventTag(Tag eventTag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEventTag(Tag eventTag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getUnfilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<List<Event>> getFilteredEventListByDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getEventTagList() {
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

        @Override
        public void updateNotificationPref(boolean set) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFavourite(String favourite) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getNotificationPref() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFavourite() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single event tag.
     */
    private class ModelStubWithEventTag extends AddEventTagCommandTest.ModelStub {
        private final Tag tag;

        ModelStubWithEventTag(Tag tag) {
            requireNonNull(tag);
            this.tag = tag;
        }

        @Override
        public boolean hasEventTag(Tag tag) {
            requireNonNull(tag);
            return this.tag.isSameTag(tag);
        }
    }

    /**
     * A Model stub that always accept the event tag being added.
     */
    private class ModelStubAcceptingEventTagAdded extends AddEventTagCommandTest.ModelStub {
        final ArrayList<Tag> eventTagsAdded = new ArrayList<>();

        @Override
        public boolean hasEventTag(Tag tag) {
            requireNonNull(tag);
            return eventTagsAdded.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public void addEventTag(Tag tag) {
            requireNonNull(tag);
            eventTagsAdded.add(tag);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddEventTagCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
