package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;
import seedu.address.testutil.TypicalCards;

public class ClearCommandSystemTest extends AppSystemTest {
    private ModelManager modelManagerWithClearedAddressBook = new ModelManager(new AddressBook(),
            TypicalCards.getTypicalTriviaBundle(), new TriviaResults(), new UserPrefs());

    @Test
    public void clear() {
        final Model defaultModel = getModel();


        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        // TODO after v1.3
        // assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        // assertSelectedCardUnchanged();
        //
        // /* Case: undo clearing address book -> original address book restored */
        // String command = UndoCommand.COMMAND_WORD;
        // String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        // assertCommandSuccess(command, expectedResultMessage, defaultModel);
        // assertSelectedCardUnchanged();
        //
        // /* Case: redo clearing address book -> cleared */
        // command = RedoCommand.COMMAND_WORD;
        // expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        // assertCommandSuccess(command, expectedResultMessage, modelManagerWithClearedAddressBook);
        // assertSelectedCardUnchanged();
        //
        // /* Case: selects first card in person list and clears address book -> cleared and no card selected */
        // executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        // selectCard(Index.fromOneBased(1));
        // assertCommandSuccess(ClearCommand.COMMAND_WORD);
        // assertSelectedCardDeselected();
        //
        // /* Case: filters the person list before clearing -> entire address book cleared */
        // executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        // showCardsWithQuestion(KEYWORD_MATCHING_WHAT);
        // assertCommandSuccess(ClearCommand.COMMAND_WORD);
        // assertSelectedCardUnchanged();
        //
        // /* Case: clear empty address book -> cleared */
        // assertCommandSuccess(ClearCommand.COMMAND_WORD);
        // assertSelectedCardUnchanged();
        //
        // /* Case: mixed case command word -> rejected */
        // assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     *
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {

        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, modelManagerWithClearedAddressBook);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
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
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
