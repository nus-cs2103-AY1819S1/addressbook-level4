package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAssignmentAtIndex;
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
import seedu.address.model.person.User;
import seedu.address.model.project.Assignment;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteAssignmentCommand}.
 */
public class DeleteAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Assignment assignmentToDelete = model.getFilteredAssignmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                assignmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        expectedModel.deleteAssignment(assignmentToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAssignmentAtIndex(model, INDEX_FIRST_PERSON);

        Assignment assignmentToDelete = model.getFilteredAssignmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                assignmentToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.deleteAssignment(assignmentToDelete);
        expectedModel.commitAddressBook();
        showNoAssignment(expectedModel);

        assertCommandSuccess(deleteAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAssignmentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of assignment list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAssignmentList().getAssignmentList().size());

        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    /*@Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Assignment assignmentToDelete = model.getFilteredAssignmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.deleteAssignment(assignmentToDelete);
        expectedModel.commitAddressBook();

        // delete -> first assignment deleted
        deleteAssignmentCommand.execute(model, commandHistory);

        // undo -> reverts assignment list back to previous state and filtered assignment list to show all assignments
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first assignment deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(outOfBoundIndex);

        // execution failed -> assignment list state not added into model
        assertCommandFailure(deleteAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);

        // single assignment list state in model -> undoCommand and redoCommand fail
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
    /*@Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteAssignmentCommand deleteCommand = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());

        showAssignmentAtIndex(model, INDEX_SECOND_PERSON);
        Assignment assignmentToDelete = model.getFilteredAssignmentList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deleteAssignment(assignmentToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second assignment in unfiltered assignment list /
        // first assignment in filtered assignment list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts assignmentlist back to previous state and filtered assignment list to show all assignments
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(assignmentToDelete, model.getFilteredAssignmentList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second assignment in unfiltered assignment list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void equals() {
        DeleteAssignmentCommand deleteFirstCommand = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);
        DeleteAssignmentCommand deleteSecondCommand = new DeleteAssignmentCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAssignmentCommand deleteFirstCommandCopy = new DeleteAssignmentCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different assignment -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAssignment(Model model) {
        model.updateFilteredAssignmentList(p -> false);

        assertTrue(model.getFilteredAssignmentList().isEmpty());
    }
}
