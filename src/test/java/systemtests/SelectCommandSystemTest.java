package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.restaurant.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.restaurant.logic.commands.menu.SelectItemCommand.MESSAGE_SELECT_ITEM_SUCCESS;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.TestUtil.getLastIndex;
import static seedu.restaurant.testutil.TestUtil.getMidIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_EGG;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.menu.SelectItemCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.testutil.account.AccountBuilder;

public class SelectCommandSystemTest extends RestaurantBookSystemTest {

    private Model model;

    @Before
    public void prepare() {
        model = getModel();
        postNow(new LoginEvent(new AccountBuilder().build()));
    }

    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the item list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST);

        /* Case: select the last card in the item list -> selected */
        Index itemCount = getLastIndex(model);
        command = SelectItemCommand.COMMAND_WORD + " " + itemCount.getOneBased();
        assertCommandSuccess(command, itemCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the item list -> selected */
        Index middleIndex = getMidIndex(model);
        command = SelectItemCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered item list, select index within bounds of restaurant book but out of bounds of item list
         * -> rejected
         */
        showItemsWithName(KEYWORD_MATCHING_EGG);
        int invalidIndex = model.getRestaurantBook().getItemList().size();
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: filtered item list, select index within bounds of restaurant book and item list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < model.getFilteredItemList().size());
        command = SelectItemCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = model.getFilteredItemList().size() + 1;
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty restaurant book -> rejected */
        deleteAllItems();
        assertCommandFailure(SelectItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected item.<br>
     * 4. {@code Storage} and {@code ItemListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see RestaurantBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SELECT_ITEM_SUCCESS,
                expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getItemListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ItemListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
