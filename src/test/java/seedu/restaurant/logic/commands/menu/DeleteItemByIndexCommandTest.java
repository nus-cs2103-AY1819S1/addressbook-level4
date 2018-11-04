package seedu.restaurant.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.MenuCommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;
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
import seedu.restaurant.model.menu.Item;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteItemByIndexCommand}.
 */
public class DeleteItemByIndexCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);

        String expectedMessage = String.format(DeleteItemByIndexCommand.MESSAGE_DELETE_ITEM_SUCCESS, 1);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(outOfBoundIndex, outOfBoundIndex);

        assertCommandFailure(deleteItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredListMultiple_success() {
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_THIRD);
        String expectedMessage = String.format(DeleteItemByIndexCommand.MESSAGE_DELETE_ITEM_SUCCESS, 3);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        for (int i = INDEX_THIRD.getZeroBased(); i >= INDEX_FIRST.getZeroBased(); i--) {
            Item itemToDelete = model.getFilteredItemList().get(i);
            expectedModel.deleteItem(itemToDelete);
        }
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);

        String expectedMessage = String.format(DeleteItemByIndexCommand.MESSAGE_DELETE_ITEM_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();
        showNoItem(expectedModel);

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getItemList().size());

        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(outOfBoundIndex, outOfBoundIndex);

        assertCommandFailure(deleteItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first item deleted
        deleteItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered item list to show all items
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(outOfBoundIndex, outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Item} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be shown
     * now. Verify that the index of the previously deleted item in the unfiltered list is different from the index at
     * the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the item object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemDeleted() throws Exception {
        DeleteItemByIndexCommand deleteItemCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showItemAtIndex(model, INDEX_SECOND);
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second item in unfiltered item list / first item in filtered item list
        deleteItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered item list to show all items
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(itemToDelete, model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second item in unfiltered item list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteItemByIndexCommand deleteFirstCommand = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);
        DeleteItemByIndexCommand deleteSecondCommand = new DeleteItemByIndexCommand(INDEX_SECOND, INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteItemByIndexCommand deleteFirstCommandCopy = new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST);
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
    private void showNoItem(Model model) {
        model.updateFilteredItemList(p -> false);

        assertTrue(model.getFilteredItemList().isEmpty());
    }
}
