package seedu.inventory.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.Item;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitInventory();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(), new StaffList());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitInventory();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getItemList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(), new StaffList());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitInventory();

        // delete -> first item deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> inventory state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Item} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted item in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the item object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(), new StaffList());

        showItemAtIndex(model, INDEX_SECOND_ITEM);
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitInventory();

        // delete -> deletes second item in unfiltered item list / first item in filtered item list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(itemToDelete, model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()));
        // redo -> deletes same second item in unfiltered item list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ITEM);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredItemList(p -> false);

        assertTrue(model.getFilteredItemList().isEmpty());
    }
}
