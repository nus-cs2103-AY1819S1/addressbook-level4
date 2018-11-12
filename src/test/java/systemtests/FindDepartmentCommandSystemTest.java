package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_TECH;

import org.junit.Test;

import seedu.address.logic.commands.FindDepartmentCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindDepartmentCommandSystemTest extends SsenisubSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindDepartmentCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TECH + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA); // departments of Carl and Fiona are "Tech"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find department command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindDepartmentCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TECH;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 2 person found */
        command = FindDepartmentCommand.COMMAND_WORD + " Marketing";
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 4 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Marketing Tech";
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 4 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Tech Marketing";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 4 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Tech Marketing Tech";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 4 persons found
         */
        command = FindDepartmentCommand.COMMAND_WORD + " Tech Marketing NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find person in address book, keyword is same as name but of different case -> 2 person found */
        command = FindDepartmentCommand.COMMAND_WORD + " TeCh";
        ModelHelper.setFilteredList(expectedModel, CARL, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of department -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Tec";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, department is substring of keyword -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Techs";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find department not in address book -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " Illegal";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " " + FIONA.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " " + FIONA.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindDepartmentCommand.COMMAND_WORD + " " + FIONA.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd-d Tech";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
