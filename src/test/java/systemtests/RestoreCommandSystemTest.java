package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.RestoreCommand.MESSAGE_RESTORED_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getArchivedPerson;
import static seedu.address.testutil.TestUtil.getLastArchiveIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class RestoreCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_RESTORED_COMMAND_FORMAT =
          String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE);

    @Test
    public void restore() {
        /* ----------------- Performing restore operation while an unfiltered list is being shown
        -------------------- */

        /* Case: restore the first person in the list, command with leading spaces and trailing spaces -> restored */
        Model expectedModel = getModel();
        String command = "     " + RestoreCommand.COMMAND_WORD
                + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        Person restoredPerson = restorePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_RESTORED_PERSON_SUCCESS, restoredPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: restore the last person in the list -> restored */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastArchiveIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person restored again */
        command = RedoCommand.COMMAND_WORD;
        restorePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing restore operation while a filtered list is being shown
        ---------------------- */

        /* Case: filtered person list, restore index within bounds of address book and person list -> restored */
        showPersonsArchivedWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getArchivedPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, restore index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsArchivedWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RestoreCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);


        /* --------------------------------- Performing invalid restore operation
        ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORED_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORED_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = RestoreCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_RESTORED_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_RESTORED_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Person} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private Person restorePerson(Model model, Index index) {
        Person targetPerson = getArchivedPerson(model, index);
        model.restorePerson(targetPerson);
        return targetPerson;
    }

    /**
     * Restores the person at {@code toRestore} by creating a default {@code RestoreCommand} using {@code toRestore} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toRestore) {
        Model expectedModel = getModel();
        Person restoredPerson = restorePerson(expectedModel, toRestore);
        String expectedResultMessage = String.format(MESSAGE_RESTORED_PERSON_SUCCESS, restoredPerson);

        assertCommandSuccess(
                RestoreCommand.COMMAND_WORD + " "
                        + toRestore.getOneBased(), expectedModel, expectedResultMessage);
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
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);

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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
