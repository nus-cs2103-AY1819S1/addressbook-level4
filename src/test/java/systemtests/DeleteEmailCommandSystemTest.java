package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_EMAIL_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalEmails.CAMP_EMAIL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteEmailCommand;
import seedu.address.model.Model;

public class DeleteEmailCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_EMAIL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEmailCommand.MESSAGE_USAGE);

    @Test
    public void delete() {

        /* Case: delete the first email in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        saveComposedEmailWithoutDisplay(CAMP_EMAIL);
        String command = "    " + DeleteEmailCommand.COMMAND_WORD + "   " + CAMP_EMAIL.getSubject();
        String expectedResultMessage = String.format(DeleteEmailCommand.MESSAGE_SUCCESS, CAMP_EMAIL.getSubject());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* ----------------------------- Performing invalid delete email operation -----------------------------------*/

        /* Case: email does not exist -> rejected */
        command = DeleteEmailCommand.COMMAND_WORD + " nonExistentEmailSubject";
        assertCommandFailure(command, String.format(MESSAGE_EMAIL_DOES_NOT_EXIST, "nonExistentEmailSubject"));

        /* Case: invalid argument (blank space) -> rejected */
        assertCommandFailure(DeleteEmailCommand.COMMAND_WORD + "   ", MESSAGE_INVALID_DELETE_EMAIL_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("dElEte_EmAil Meeting", MESSAGE_UNKNOWN_COMMAND);
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
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
