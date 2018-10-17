package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.inventory.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;
import static seedu.inventory.testutil.TestUtil.getItem;
import static seedu.inventory.testutil.TestUtil.getLastIndex;
import static seedu.inventory.testutil.TestUtil.getMidIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalItems.KEYWORD_MATCHING_SAMSUNG;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.DeleteCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;

public class DeleteCommandSystemTest extends InventorySystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first item in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_ITEM.getOneBased() + "       ";
        Item deletedItem = removeItem(expectedModel, INDEX_FIRST_ITEM);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItem);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last item in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastItemIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastItemIndex);

        /* Case: undo deleting the last item in the list -> last item restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last item in the list -> last item deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeItem(modelBeforeDeletingLast, lastItemIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle item in the list -> deleted */
        Index middleItemIndex = getMidIndex(getModel());
        assertCommandSuccess(middleItemIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered item list, delete index within bounds of inventory and item list -> deleted */
        showItemsWithName(KEYWORD_MATCHING_SAMSUNG);
        Index index = INDEX_FIRST_ITEM;
        assertTrue(index.getZeroBased() < getModel().getFilteredItemList().size());
        assertCommandSuccess(index);

        /* Case: filtered item list, delete index within bounds of inventory but out of bounds of item list
         * -> rejected
         */
        showItemsWithName(KEYWORD_MATCHING_SAMSUNG);
        int invalidIndex = getModel().getInventory().getItemList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a item card is selected ------------------------ */

        /* Case: delete the selected item -> item list panel selects the item before the deleted item */
        showAllItems();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectItem(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedItem = removeItem(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItem);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getInventory().getItemList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Item} at the specified {@code index} in {@code model}'s inventory.
     * @return the removed item
     */
    private Item removeItem(Model model, Index index) {
        Item targetItem = getItem(model, index);
        model.deleteItem(targetItem);
        return targetItem;
    }

    /**
     * Deletes the item at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Item deletedItem = removeItem(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ITEM_SUCCESS, deletedItem);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see InventorySystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
