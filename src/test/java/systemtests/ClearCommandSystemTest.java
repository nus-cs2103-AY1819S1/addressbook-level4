package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ContactContainsRoomPredicate;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;

public class ClearCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + "   all");
        assertSelectedCardUnchanged();

        /* Case: undo clearing address book -> original address book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing address book -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: filters the person list before clearing -> entire address book cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_WORD + " all");
        assertSelectedCardUnchanged();

        //@@author kengwoon
        /* Case: clear specific cca -> cleared person(s) associated with cca */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertSpecificCommandSuccess(ClearCommand.COMMAND_WORD + " soccer");
        assertSelectedCardUnchanged();

        /* Case: clear specific room -> cleared person(s) associated with room */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertSpecificCommandSuccess(ClearCommand.COMMAND_WORD + " E321");
        assertSelectedCardUnchanged();

        /* Case: clear specific cca & room -> cleared person(s) associated with cca & room */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertSpecificCommandSuccess(ClearCommand.COMMAND_WORD + " soccer" + " E321");
        assertSelectedCardUnchanged();

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD + " all");
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
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        String trimmedArgs = command.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");
        assertCommandSuccess(command, ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS, new ModelManager());
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

    //@@author kengwoon
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_CLEAR_SPECIFIC_SUCCESS} and the model related components equal to an
     * empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertSpecificCommandSuccess(String command) {
        String trimmedArgs = command.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        List<String> keywords = new ArrayList<>();
        for (int i = 1; i < nameKeywords.length; i++) {
            keywords.add(nameKeywords[i]);
        }

        Model expectedModel = getModel();
        List<Person> toClear = new ArrayList<>();
        ContactContainsTagPredicate predicateTag = new ContactContainsTagPredicate(keywords);
        ContactContainsRoomPredicate predicateRoom = new ContactContainsRoomPredicate(keywords);
        for (Person p : expectedModel.getFilteredPersonList()) {
            if (predicateRoom.test(p) || predicateTag.test(p)) {
                toClear.add(p);
            }
        }
        expectedModel.clearMultiplePersons(toClear);
        assertCommandSuccess(command, String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, keywords),
                                expectedModel);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
