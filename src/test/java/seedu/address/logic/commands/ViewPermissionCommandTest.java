package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewPermissionCommand.
 */
public class ViewPermissionCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        model.setLoggedInUser(User.getAdminUser());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        expectedModel.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute() {

        //View permission of first index.
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(ViewPermissionCommand.MESSAGE_SUCCESS_DISPLAY_PERMISSION, p.getName(),
                p.getPermissionSet());
        assertCommandSuccess(new ViewPermissionCommand(INDEX_FIRST_PERSON), model, commandHistory,
                expectedMessage, expectedModel);

        //1 index out of bounds.
        Index invalidIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertCommandFailure(new ViewPermissionCommand(invalidIndex), model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        ViewPermissionCommand firstCommand = new ViewPermissionCommand(INDEX_FIRST_PERSON);
        ViewPermissionCommand secondCommand = new ViewPermissionCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ViewPermissionCommand firstCommandCopy = new ViewPermissionCommand(INDEX_FIRST_PERSON);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

}
