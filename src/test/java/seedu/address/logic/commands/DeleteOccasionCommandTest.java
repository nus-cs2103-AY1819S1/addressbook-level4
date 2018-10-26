package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.showOccasionAtIndex;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasionsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.occasion.Occasion;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteOccasionCommand}.
 */
public class DeleteOccasionCommandTest {

    private Model model = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);

        String expectedMessage = String.format(DeleteOccasionCommand.MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(outOfBoundIndex);

        assertCommandFailure(deleteOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);

        String expectedMessage = String.format(DeleteOccasionCommand.MESSAGE_DELETE_OCCASION_SUCCESS,
                occasionToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();
        showNoOccasion(expectedModel);

        assertCommandSuccess(deleteOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        Index outOfBoundIndex = INDEX_SECOND_OCCASION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOccasionList().size());

        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(outOfBoundIndex);

        assertCommandFailure(deleteOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        // delete -> first occasion deleted
        deleteOccasionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered occasion list to show all occasions
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first occasion deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Occasion} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted occasion in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the occasion object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOccasionDeleted() throws Exception {
        DeleteOccasionCommand deleteOccasionCommand = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showOccasionAtIndex(model, INDEX_SECOND_OCCASION);
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second occasion in unfiltered occasion list / first occasion in filtered occasion list
        deleteOccasionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered occasion list to show all occasions
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(occasionToDelete, model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased()));
        // redo -> deletes same second occasion in unfiltered occasion list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteOccasionCommand deleteFirstCommand = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);
        DeleteOccasionCommand deleteSecondCommand = new DeleteOccasionCommand(INDEX_SECOND_OCCASION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteOccasionCommand deleteFirstCommandCopy = new DeleteOccasionCommand(INDEX_FIRST_OCCASION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different occasion -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOccasion(Model model) {
        model.updateFilteredOccasionList(p -> false);

        assertTrue(model.getFilteredOccasionList().isEmpty());
    }
}
