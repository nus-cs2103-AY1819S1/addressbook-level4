package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtDateAndIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExistingPersonInEventPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SeeEventContactsCommand}.
 */
public class SeeEventContactsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Set<Person> eventContacts = event.getEventContacts();
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(eventContacts);

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());
    }

    @Test
    public void execute_invalidDateOrIndexIndexUnfilteredList_throwsCommandException() {

        // checks for both inputs to the command

        EventDate outOfBoundDate = new EventDate("2019-12-31");
        SeeEventContactsCommand command =
                new SeeEventContactsCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        assertCommandFailure(command, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);

        // meeting is top of event list by date (latest date first)
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventListByDate().get(0).size() + 1);
        command = new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        assertCommandFailure(command, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateAndIndexFilteredList_success() {
        // shows event MEETING
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Set<Person> eventContacts = event.getEventContacts();
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(eventContacts);

        SeeEventContactsCommand command = new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showEventAtDateAndIndex(expectedModel, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());
    }

    @Test
    public void execute_invalidDateFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        EventDate outOfBoundDate = new EventDate(VALID_EVENT_DATE_DOCTORAPPT);
        // ensures that outOfBoundDate is still in bounds of address book list (unfiltered)
        assertTrue(model.getAddressBook().getEventList().stream()
                .anyMatch(event -> event.getEventDate().equals(outOfBoundDate)));

        SeeEventContactsCommand command = new SeeEventContactsCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        SeeEventContactsCommand command = new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personDeleted_emptyPersonListShown() {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete all contacts of this event
        Set<Person> eventContacts = event.getEventContacts();
        eventContacts.stream().forEach(contact -> model.deletePerson(contact));
        eventContacts.stream().forEach(contact -> expectedModel.deletePerson(contact));

        showNoPerson(expectedModel);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_personEdited_editedPersonShown() {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Set<Person> eventContacts = event.getEventContacts();
        assertTrue(eventContacts.size() == 1);

        // edit person in eventContacts
        Person contactToEdit = new ArrayList<>(eventContacts).get(0);
        Person editedContact = new PersonBuilder(contactToEdit).withEmail("newemail@gmail.com").build();

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // edit contact
        model.updatePerson(contactToEdit, editedContact);
        expectedModel.updatePerson(contactToEdit, editedContact);

        expectedModel.updateFilteredPersonList(person -> person.equals(editedContact));

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(Arrays.asList(editedContact)), model.getFilteredPersonList());
    }

    /**
     * 1. Gets the event contact (size 1) of an {@code Event} from an unfiltered list.
     * 2. Deletes the event contact.
     * 3. Check that no event contacts shown when command is executed.
     * 4. Undo the deletion.
     * 5. Check that event contact is shown again.
     * 6. Redo the deletion.
     * 7. Check that no event contacts shown when command is executed.
     */
    @Test
    public void executeUndoRedo_personDeleted_emptyPersonListShown() throws Exception {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());

        Set<Person> eventContacts = event.getEventContacts();
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(eventContacts);
        Person contactToDelete = new ArrayList<>(eventContacts).get(0);

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        // check that contact is shown upon command execution
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // delete the only contact of this event in the filtered list with only this contact
        model.updateFilteredPersonList(person -> person.isSamePerson(contactToDelete));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model, commandHistory);

        expectedModel.deletePerson(contactToDelete);
        expectedModel.commitAddressBook();

        showNoPerson(expectedModel);

        // check that no contact is shown upon command execution
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is shown once again upon command execution
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // redo -> deletes same contact in unfiltered person list
        expectedModel.redoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is no longer shown upon command execution
        showNoPerson(expectedModel);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * 1. Gets the event contact (size 1) of an {@code Event} from an unfiltered list.
     * 2. Edit the event contact (edited contact is still same person as original contact but with different info).
     * 3. Check that event contact is shown when command is executed.
     * 4. Undo the edit.
     * 5. Check that event contact is still shown.
     * 6. Redo the edit.
     * 7. Check that event contact is shown when command is executed.
     */
    @Test
    public void executeUndoRedo_personEditedToSamePerson_contactShown() throws Exception {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());

        Set<Person> eventContacts = event.getEventContacts();
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(eventContacts);

        // create edited contact
        Person contactToEdit = new ArrayList<>(eventContacts).get(0);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(contactToEdit)
                .withEmail("newemail@gmail.com")
                .build();
        Person editedContact = new PersonBuilder(contactToEdit).withEmail("newemail@gmail.com").build();

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        // check that contact is shown upon command execution
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // edit the only contact of this event in the filtered list with only this contact
        model.updateFilteredPersonList(person -> person.isSamePerson(contactToEdit));
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        editCommand.execute(model, commandHistory);

        expectedModel.updatePerson(contactToEdit, editedContact);
        expectedModel.commitAddressBook();

        // check that edited contact is shown upon command execution
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(editedContact), model.getFilteredPersonList());

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is shown once again upon command execution
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // redo -> edits same contact in unfiltered person list
        expectedModel.redoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is shown upon command execution
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(editedContact), model.getFilteredPersonList());
    }

    /**
     * 1. Gets the event contact (size 1) of an {@code Event} from an unfiltered list.
     * 2. Edit the event contact (edited contact is different person from original contact).
     * 3. Check that event contact is shown when command is executed.
     * 4. Undo the edit.
     * 5. Check that event contact is still shown.
     * 6. Redo the edit.
     * 7. Check that event contact is shown when command is executed.
     */
    @Test
    public void executeUndoRedo_personEditedToDifferentPerson_noContactShown() throws Exception {
        Event event = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());

        Set<Person> eventContacts = event.getEventContacts();
        ExistingPersonInEventPredicate predicate = new ExistingPersonInEventPredicate(eventContacts);

        // create edited contact
        Person contactToEdit = new ArrayList<>(eventContacts).get(0);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(contactToEdit)
                .withName("Different Name")
                .build();
        Person editedContact = new PersonBuilder(contactToEdit).withName("Different Name").build();

        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(SeeEventContactsCommand.MESSAGE_CONTACTS_LISTED_OVERVIEW, event.getEventName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        // check that contact is shown upon command execution
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // edit the only contact of this event in the filtered list with only this contact
        model.updateFilteredPersonList(person -> person.isSamePerson(contactToEdit));
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        editCommand.execute(model, commandHistory);

        expectedModel.updatePerson(contactToEdit, editedContact);
        expectedModel.commitAddressBook();

        // check that no contacts shown upon command execution
        showNoPerson(expectedModel);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is shown once again upon command execution
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(eventContacts), model.getFilteredPersonList());

        // redo -> edits same contact in unfiltered person list
        expectedModel.redoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // check that contact is shown upon command execution
        showNoPerson(expectedModel);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        SeeEventContactsCommand command =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT);
        SeeEventContactsCommand differentDateCommand =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_MEETING), INDEX_FIRST_EVENT);
        SeeEventContactsCommand differentIndexCommand =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        SeeEventContactsCommand commandCopy =
                new SeeEventContactsCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT);
        assertTrue(command.equals(commandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different event date -> returns false
        assertFalse(command.equals(differentDateCommand));

        // different event index -> returns false
        assertFalse(command.equals(differentIndexCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
