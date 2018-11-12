package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.order.ClearCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearCommandSystemTest extends OrderBookSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);
        setUpOrderListPanel();

        String clearCommand = OrderCommand.COMMAND_WORD + " " + ClearCommand.COMMAND_WORD;

        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + clearCommand + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(clearCommand);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
