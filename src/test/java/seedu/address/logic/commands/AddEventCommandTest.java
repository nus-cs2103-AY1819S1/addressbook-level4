package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NONEXISTENT_EVENT_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.APPOINTMENT_TAG;
import static seedu.address.testutil.TypicalTags.MEETING_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
import seedu.address.testutil.ScheduledEventBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) for adding events including
 * persons in address book, and unit tests for
 * {@code AddEventCommand}.
 */
public class AddEventCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null, null);
    }

    @Test
    public void execute_eventAcceptedByModelNoContacts_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().build();

        Set<Index> contactIndices = new HashSet<>();

        CommandResult commandResult = new AddEventCommand(validEvent, contactIndices)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_eventAcceptedByModelWithContacts_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().withEventContacts(ALICE).build();

        Set<Index> contactIndices = new HashSet<>();
        contactIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)));

        CommandResult commandResult = new AddEventCommand(validEvent, contactIndices)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_eventAcceptedByModelWithMultipleContacts_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().withEventContacts(ALICE, BOB).build();

        Set<Index> contactIndices = new HashSet<>();
        contactIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)));
        contactIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_2)));

        CommandResult commandResult = new AddEventCommand(validEvent, contactIndices)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_eventAcceptedByModelWithTag_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().withEventTags(VALID_TAG_APPOINTMENT).build();

        Set<Index> contactIndices = new HashSet<>();

        CommandResult commandResult = new AddEventCommand(validEvent, contactIndices)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_eventAcceptedByModelWithMultipleTags_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().withEventTags(VALID_TAG_APPOINTMENT, VALID_TAG_MEETING).build();

        Set<Index> contactIndices = new HashSet<>();

        CommandResult commandResult = new AddEventCommand(validEvent, contactIndices)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_contactsValidIndexUnfilteredList_addSuccessful() {
        Person eventContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventToAdd = new ScheduledEventBuilder().withEventContacts(eventContact).build();

        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PERSON)));

        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, eventToAdd);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(eventToAdd);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_contactsInvalidIndexUnfilteredList_addSuccessful() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Event validEvent = new ScheduledEventBuilder().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(outOfBoundIndex)));

        assertCommandFailure(addEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactsValidIndexFilteredList_addSuccessful() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person eventContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventToAdd = new ScheduledEventBuilder().withEventContacts(eventContact).build();

        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PERSON)));

        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, eventToAdd);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(eventToAdd);
        expectedModel.commitAddressBook();
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(addEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTag_throwsCommandException() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new ScheduledEventBuilder().withEventTags(NONEXISTENT_EVENT_TAG).build();

        Set<Index> contactIndices = new HashSet<>();

        AddEventCommand addEventCommand = new AddEventCommand(validEvent, contactIndices);

        assertCommandFailure(addEventCommand, model, commandHistory, String.format(Messages.MESSAGE_NONEXISTENT_TAG,
                new HashSet<>(Arrays.asList(new Tag(NONEXISTENT_EVENT_TAG)))));
    }

    @Test
    public void execute_contactsInvalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(outOfBoundIndex)));

        assertCommandFailure(addEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new ScheduledEventBuilder().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent, new HashSet<>());
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);
        addEventCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_clashingEvent_throwsCommandException() throws Exception {
        Event validEvent = new ScheduledEventBuilder()
                .withEventStartTime("1200")
                .withEventEndTime("1400")
                .build();
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        Event clashingEvent = new ScheduledEventBuilder()
                .withEventStartTime("1210")
                .withEventEndTime("1410")
                .build();
        AddEventCommand addEventCommand = new AddEventCommand(clashingEvent, new HashSet<>());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_CLASHING_EVENT);
        addEventCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void executeUndoRedo_contactValidIndexUnfilteredList_success() throws Exception {
        Person eventContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventToAdd = new ScheduledEventBuilder().withEventContacts(eventContact).build();

        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PERSON)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(eventToAdd);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        addEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(outOfBoundIndex)));

        // execution failed -> address book state not added into model
        assertCommandFailure(addEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Add an event with a {@code Person} from a filtered list.
     * 2. Undo the addition of event.
     * 3. Show the unfiltered list.
     * 3. The unfiltered list should be shown now. Verify that the index of the previous person added in the event in
     * the unfiltered list is different from the index at the filtered list.
     * 4. Redo the addition. This ensures {@code RedoCommand} adds the same person object as an event contact,
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEventAdded() throws Exception {
        Event validEvent = new ScheduledEventBuilder().withEventContacts().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent,
                new HashSet<>(Arrays.asList(INDEX_FIRST_PERSON)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // adds the second person as an eventContact
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person eventContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventToAdd = new ScheduledEventBuilder().withEventContacts(eventContact).build();

        expectedModel.addEvent(eventToAdd);
        expectedModel.commitAddressBook();

        // addEventCommand -> adds an event with the second person in unfiltered person list / first person in
        // filtered person list as an eventContact
        addEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(eventContact, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        // redo -> adds an event with the same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Event firstEvent = new ScheduledEventBuilder().withEventName("event").build();
        Event secondEvent = new ScheduledEventBuilder().withEventName("a different event").build();
        AddEventCommand addFirstEventCommand = new AddEventCommand(firstEvent,
                new HashSet<>(Arrays.asList(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)))));
        AddEventCommand addSecondEventCommand = new AddEventCommand(secondEvent,
                new HashSet<>(Arrays.asList(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)))));

        // same object -> returns true
        assertTrue(addFirstEventCommand.equals(addFirstEventCommand));

        // same values -> returns true
        AddEventCommand addFirstEventCommandCopy = new AddEventCommand(firstEvent,
                new HashSet<>(Arrays.asList(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)))));
        assertTrue(addFirstEventCommand.equals(addFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(addFirstEventCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(addFirstEventCommand.equals(null));

        // different event -> returns false
        assertFalse(addFirstEventCommand.equals(addSecondEventCommand));
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
        public void updateNotificationPref(boolean set)  {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getNotificationPref()  {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFavourite(String favourite)  {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFavourite(Event favourite)  {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isFavourite(Event event)   {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFavourite()  {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
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

        @Override
        public boolean hasClashingEvent(Event event) {
            requireNonNull(event);
            return this.event.isClashingEvent(event);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();
        final ArrayList<Person> personsAdded = new ArrayList<>(Arrays.asList(ALICE, BOB));
        final ArrayList<Tag> eventTagsAdded = new ArrayList<>(Arrays.asList(APPOINTMENT_TAG, MEETING_TAG));

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public boolean hasEventTag(Tag tag) {
            requireNonNull(tag);
            return eventTagsAdded.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public boolean hasClashingEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isClashingEvent);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            ObservableList<Person> personObservableList = FXCollections.observableArrayList();
            personObservableList.addAll(personsAdded);
            return personObservableList;
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddEventCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
