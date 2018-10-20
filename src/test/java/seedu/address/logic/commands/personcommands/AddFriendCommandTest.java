package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

import java.util.ArrayList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AddFriendCommand}.
 */
public class AddFriendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person person1 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_FIRST.getZeroBased(),
                INDEX_SECOND.getZeroBased()));

        String expectedMessage = String.format(AddFriendCommand.MESSAGE_ADD_FRIEND_SUCCESS,
                person1.getName(), person2.getName());

        Person newPerson1 = new Person(person1);
        Person newPerson2 = new Person(person2);
        ArrayList<Person> friendList1 = newPerson1.getFriends();
        ArrayList<Person> friendList2 = newPerson2.getFriends();
        friendList1.add(person2);
        friendList2.add(person1);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(person1, newPerson1, person2, newPerson2);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addFriendCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1,
                model.getFilteredPersonList().size() + 2);
        AddFriendCommand addFriendCommand = new AddFriendCommand(outOfBoundIndexes);

        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person person1 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_FIRST.getZeroBased(),
                INDEX_SECOND.getZeroBased()));

        Person newPerson1 = new Person(person1);
        Person newPerson2 = new Person(person2);
        ArrayList<Person> friendList1 = newPerson1.getFriends();
        ArrayList<Person> friendList2 = newPerson2.getFriends();
        friendList1.add(person2);
        friendList2.add(person1);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(person1, newPerson1, person2, newPerson2);
        expectedModel.commitAddressBook();

        // addFriend command executed
        addFriendCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> addFriend command executed again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1,
                model.getFilteredPersonList().size() + 2);
        AddFriendCommand addFriendCommand = new AddFriendCommand(outOfBoundIndexes);

        // execution failed -> address book state not added into model
        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        AddFriendCommand addFriendFirstCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_FIRST.getZeroBased(),
                INDEX_SECOND.getZeroBased()));
        AddFriendCommand addFriendSecondCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));

        // same object -> returns true
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommand));

        // same values -> returns true
        AddFriendCommand addFriendFirstCommandCopy = new AddFriendCommand(Index.fromZeroBased(INDEX_FIRST.getZeroBased(),
                INDEX_SECOND.getZeroBased()));
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFriendFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFriendFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addFriendFirstCommand.equals(addFriendSecondCommand));
    }
}
