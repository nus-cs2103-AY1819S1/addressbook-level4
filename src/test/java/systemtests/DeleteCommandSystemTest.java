package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.order.DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getOrder;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.order.DeleteCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

public class DeleteCommandSystemTest extends OrderBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    private static final String DELETE_COMMAND = OrderCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD;

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);
        setUpOrderListPanel();

        /* Case: delete the first common in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        command = "     " + DELETE_COMMAND + "      " + INDEX_FIRST.getOneBased() + "       ";
        Order deletedOrder = removeOrder(expectedModel, INDEX_FIRST);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last common in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: delete the middle common in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* --------------------- Performing delete operation while a common card is selected ------------------------ */

        /* Case: delete the selected common -> common list panel selects the common before the deleted common */
        showAllOrders();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectOrder(selectedIndex);
        command = DELETE_COMMAND + " " + selectedIndex.getOneBased();
        deletedOrder = removeOrder(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DELETE_COMMAND + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DELETE_COMMAND + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getOrderBook().getOrderList().size() + 1);
        command = DELETE_COMMAND + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DELETE_COMMAND + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DELETE_COMMAND + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Person} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the removed common
     */
    private Order removeOrder(Model model, Index index) {
        Order targetOrder = getOrder(model, index);
        model.deleteOrder(targetOrder);
        return targetOrder;
    }

    /**
     * Deletes the common at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Order deletedOrder = removeOrder(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);

        assertCommandSuccess(
                DELETE_COMMAND + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        setUpOrderListPanel();
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
