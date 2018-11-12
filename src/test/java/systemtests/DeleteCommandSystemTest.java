package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.superta.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.superta.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.superta.testutil.TestUtil.getLastIndex;
import static seedu.superta.testutil.TestUtil.getMidIndex;
import static seedu.superta.testutil.TestUtil.getPerson;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.superta.commons.core.Messages;
import seedu.superta.commons.core.index.Index;
import seedu.superta.logic.commands.DeleteCommand;
import seedu.superta.logic.commands.RedoCommand;
import seedu.superta.logic.commands.UndoCommand;
import seedu.superta.model.Model;
import seedu.superta.model.student.Student;

public class DeleteCommandSystemTest extends SuperTaClientSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first student in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        Student deletedStudent = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedStudent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last student in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last student in the list -> last student restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last student in the list -> last student deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle student in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered student list, delete index within bounds of address book and student list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredStudentList().size());
        assertCommandSuccess(index);

        /* Case: filtered student list, delete index within bounds of address book but out of bounds of student list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getSuperTaClient().getStudentList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* ------------------ Performing delete operation while a student card is selected ------------------------ */

        /* Case: delete the selected student -> student list panel selects the student before the deleted student */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedStudent = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedStudent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* ------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
            getModel().getSuperTaClient().getStudentList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Student} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the removed student
     */
    private Student removePerson(Model model, Index index) {
        Student targetStudent = getPerson(model, index);
        model.deleteStudent(targetStudent);
        return targetStudent;
    }

    /**
     * Deletes the student at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Student deletedStudent = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedStudent);

        assertCommandSuccess(
            DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            // pass
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
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
