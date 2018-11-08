package seedu.parking.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.logic.commands.CommandTestUtil.showCarparkAtIndex;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;
import static seedu.parking.testutil.TypicalIndexes.INDEX_SECOND_CARPARK;

import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.model.carpark.Carpark;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Carpark carparkToDelete = model.getFilteredCarparkList().get(INDEX_FIRST_CARPARK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARPARK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARPARK_SUCCESS, carparkToDelete);

        ModelManager expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());
        expectedModel.deleteCarpark(carparkToDelete);
        expectedModel.commitCarparkFinder();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCarparkList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCarparkAtIndex(model, INDEX_FIRST_CARPARK);

        Carpark carparkToDelete = model.getFilteredCarparkList().get(INDEX_FIRST_CARPARK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARPARK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARPARK_SUCCESS, carparkToDelete);

        Model expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());
        expectedModel.deleteCarpark(carparkToDelete);
        expectedModel.commitCarparkFinder();
        showNoCarpark(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCarparkAtIndex(model, INDEX_FIRST_CARPARK);

        Index outOfBoundIndex = INDEX_SECOND_CARPARK;
        // ensures that outOfBoundIndex is still in bounds of car park finder list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCarparkFinder().getCarparkList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Carpark carparkToDelete = model.getFilteredCarparkList().get(INDEX_FIRST_CARPARK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARPARK);
        Model expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());
        expectedModel.deleteCarpark(carparkToDelete);
        expectedModel.commitCarparkFinder();

        // delete -> first car park deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts CarparkFinder back to previous state and filtered car park list to show all carparks
        expectedModel.undoCarparkFinder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first car park deleted again
        expectedModel.redoCarparkFinder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCarparkList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> car park finder state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);

        // single CarparkFinder state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Carpark} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted carpark in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the carpark object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCarparkDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARPARK);
        Model expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());

        showCarparkAtIndex(model, INDEX_SECOND_CARPARK);
        Carpark carparkToDelete = model.getFilteredCarparkList().get(INDEX_FIRST_CARPARK.getZeroBased());
        expectedModel.deleteCarpark(carparkToDelete);
        expectedModel.commitCarparkFinder();

        // delete -> deletes second car park in unfiltered car park list / first car park in filtered car park list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts CarparkFinder back to previous state and filtered car park list to show all carparks
        expectedModel.undoCarparkFinder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(carparkToDelete, model.getFilteredCarparkList().get(INDEX_FIRST_CARPARK.getZeroBased()));
        // redo -> deletes same second carpark in unfiltered car park list
        expectedModel.redoCarparkFinder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CARPARK);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CARPARK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CARPARK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different carpark -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCarpark(Model model) {
        model.updateFilteredCarparkList(p -> false);

        assertTrue(model.getFilteredCarparkList().isEmpty());
    }
}
