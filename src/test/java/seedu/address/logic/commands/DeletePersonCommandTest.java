package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validNamePhoneUnfilteredList_success() {
        Optional<Person> result = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(ALICE.getName())
                        && person.getPhone().equals(ALICE.getPhone()))
                .findFirst();
        Person personToDelete = null;
        if (result.isPresent()) {
            personToDelete = result.get();
        }
        assertNotNull(personToDelete);
        DeletePersonCommand deletePersonCommand =
                new DeletePersonCommand(personToDelete.getName(), personToDelete.getPhone());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deletePersonCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNamePhoneUnfilteredList_throwsCommandException() {
        Name invalidName = new Name("JASKLFJA12412445");
        Phone invalidPhone = new Phone("12412412");
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(invalidName, invalidPhone);

        assertCommandFailure(deletePersonCommand, model, commandHistory,
                DeletePersonCommand.MESSAGE_INVALID_DELETE_PERSON);
    }

    @Test
    public void execute_validNamePhoneFilteredList_success() {
        // showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Optional<Person> result = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(ALICE.getName())
                        && person.getPhone().equals(ALICE.getPhone()))
                .findFirst();
        Person personToDelete = null;
        if (result.isPresent()) {
            personToDelete = result.get();
        }
        assertNotNull(personToDelete);
        DeletePersonCommand deletePersonCommand =
                new DeletePersonCommand(personToDelete.getName(), personToDelete.getPhone());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        // showNoPerson(expectedModel);

        assertCommandSuccess(deletePersonCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNamePhoneFilteredList_throwsCommandException() {
        // showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Name invalidName = new Name("JASKLFJA12412445");
        Phone invalidPhone = new Phone("12412412");
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(invalidName, invalidPhone);

        assertCommandFailure(deletePersonCommand, model, commandHistory,
                DeletePersonCommand.MESSAGE_INVALID_DELETE_PERSON);
    }

    @Test
    public void executeUndoRedo_validNamePhoneUnfilteredList_success() throws Exception {
        Optional<Person> result = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(ALICE.getName())
                        && person.getPhone().equals(ALICE.getPhone()))
                .findFirst();
        Person personToDelete = null;
        if (result.isPresent()) {
            personToDelete = result.get();
        }
        assertNotNull(personToDelete);
        DeletePersonCommand deletePersonCommand =
                new DeletePersonCommand(personToDelete.getName(), personToDelete.getPhone());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deletePersonCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidNamePhoneUnfilteredList_failure() {
        Name invalidName = new Name("JASKLFJA12412445");
        Phone invalidPhone = new Phone("12412412");
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(invalidName, invalidPhone);

        // execution failed -> address book state not added into model
        assertCommandFailure(deletePersonCommand, model, commandHistory,
                DeletePersonCommand.MESSAGE_INVALID_DELETE_PERSON);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validNamePhoneFilteredList_samePersonDeleted() throws Exception {
        Optional<Person> result = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(ALICE.getName())
                        && person.getPhone().equals(ALICE.getPhone()))
                .findFirst();
        Person personToDelete = null;
        if (result.isPresent()) {
            personToDelete = result.get();
        }
        assertNotNull(personToDelete);
        DeletePersonCommand deletePersonCommand =
                new DeletePersonCommand(personToDelete.getName(), personToDelete.getPhone());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // showPersonAtIndex(model, INDEX_SECOND_PERSON);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deletePersonCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeletePersonCommand deleteFirstCommand = new DeletePersonCommand(ALICE.getName(),
                ALICE.getPhone());
        DeletePersonCommand deleteSecondCommand = new DeletePersonCommand(BENSON.getName(),
                BENSON.getPhone());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePersonCommand deleteFirstCommandCopy = new DeletePersonCommand(ALICE.getName(),
                ALICE.getPhone());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
