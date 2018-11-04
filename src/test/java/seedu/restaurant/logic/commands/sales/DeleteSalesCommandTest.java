package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.sales.SalesCommandTestUtil.showRecordAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteSalesCommand}.
 */
public class DeleteSalesCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteSalesCommand.MESSAGE_DELETE_SALES_SUCCESS, recordToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST);

        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteSalesCommand.MESSAGE_DELETE_SALES_SUCCESS, recordToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitRestaurantBook();
        showNoRecord(expectedModel);

        assertCommandSuccess(deleteSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecordAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getRecordList().size());

        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first record deleted
        deleteSalesCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered record list to show all records
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first record deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code SalesRecord} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be
     * shown now. Verify that the index of the previously deleted record in the unfiltered list is different from the
     * index at the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the record object
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordDeleted() throws Exception {
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showRecordAtIndex(model, INDEX_SECOND);
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second record in unfiltered record list / first record in filtered record list
        deleteSalesCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered record list to show all records
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(recordToDelete, model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased()));

        // redo -> deletes same second record in unfiltered record list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteSalesCommand deleteSalesFirstCommand = new DeleteSalesCommand(INDEX_FIRST);
        DeleteSalesCommand deleteSalesSecondCommand = new DeleteSalesCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteSalesFirstCommand.equals(deleteSalesFirstCommand));

        // same values -> returns true
        DeleteSalesCommand deleteSalesFirstCommandCopy = new DeleteSalesCommand(INDEX_FIRST);
        assertTrue(deleteSalesFirstCommand.equals(deleteSalesFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteSalesFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSalesFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteSalesFirstCommand.equals(deleteSalesSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no record.
     */
    private void showNoRecord(Model model) {
        model.updateFilteredRecordList(p -> false);
        assertTrue(model.getFilteredRecordList().isEmpty());
    }
}
