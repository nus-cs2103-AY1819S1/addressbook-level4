package systemtests;

import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.parking.testutil.TypicalCarparks.KEYWORD_MATCHING_SENGKANG;

import org.junit.Test;

import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.RedoCommand;
import seedu.parking.logic.commands.UndoCommand;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.carpark.Carpark;

public class ClearCommandSystemTest extends CarparkFinderSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty car park finder, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardChangedMulti(new Carpark[]{});

        /* Case: undo clearing car park finder -> original car park finder restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing car park finder -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in car park list and clears car park finder -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original car park finder
        selectCarpark(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardChangedMulti(new Carpark[]{});

        /* Case: filters the car park list before clearing -> entire car park finder cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original car park finder
        showCarparksWithName(KEYWORD_MATCHING_SENGKANG);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardChangedMulti(new Carpark[]{});

        /* Case: clear empty car park finder -> cleared */
        command = ClearCommand.COMMAND_WORD;
        expectedResultMessage = ClearCommand.MESSAGE_EMPTY;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
