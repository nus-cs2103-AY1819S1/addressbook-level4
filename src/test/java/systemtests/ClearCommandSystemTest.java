package systemtests;

import static seedu.restaurant.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_EGG;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.commands.ClearCommand;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.util.SampleDataUtil;
import seedu.restaurant.testutil.account.AccountBuilder;

public class ClearCommandSystemTest extends RestaurantBookSystemTest {

    private Model model;

    @Before
    public void prepare() {
        model = getModel();
        postNow(new LoginEvent(new AccountBuilder().build()));
    }

    @Test
    public void clear() {
        /* Case: clear non-empty restaurant book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing restaurant book -> original restaurant book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, model);
        assertSelectedCardUnchanged();

        /* Case: redo clearing restaurant book -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage,
                new ModelManager(SampleDataUtil.getSampleRestaurantBook(), new UserPrefs()));
        assertSelectedCardUnchanged();

        /* Case: selects first card in item list and clears restaurant book -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original restaurant book
        selectItem(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardDeselected();

        /* Case: filters the item list before clearing -> entire restaurant book cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original restaurant book
        showItemsWithName(KEYWORD_MATCHING_EGG);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: clear empty restaurant book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display box
     * displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model. These
     * verifications are done by {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String,
     * Model)}.<br> Also verifies that the command box has the default style class and the status bar's sync status
     * changes.
     *
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS,
                new ModelManager(SampleDataUtil.getSampleRestaurantBook(), new UserPrefs()));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays {@code
     * expectedResultMessage} and the model related components equal to {@code expectedModel}.
     *
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display box
     * displays {@code expectedResultMessage} and the model related components equal to the current model. These
     * verifications are done by {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String,
     * Model)}.<br> Also verifies that the browser url, selected card and status bar remain unchanged, and the command
     * box has the error style.
     *
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
