package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.meeting.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.meeting.logic.commands.SelectCommand.MESSAGE_SELECT_GROUP_SUCCESS;
import static seedu.meeting.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.meeting.testutil.TestUtil.getGroupLastIndex;
import static seedu.meeting.testutil.TestUtil.getGroupMidIndex;
import static seedu.meeting.testutil.TestUtil.getPersonLastIndex;
import static seedu.meeting.testutil.TestUtil.getPersonMidIndex;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.meeting.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.RedoCommand;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.commands.UndoCommand;
import seedu.meeting.model.Model;

public class SelectCommandSystemTest extends MeetingBookSystemTest {
    @Test
    public void select() {
        /* -------------------- Perform select operations on the shown unfiltered person list ----------------------- */

        /* Case: select the first card in the person list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " p/" + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON);

        /* Case: select the last card in the person list -> selected */
        Index personCount = getPersonLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " p/" + personCount.getOneBased();
        assertCommandSuccess(command, personCount, SelectCommand.SelectCommandType.PERSON);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = getPersonMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " p/" + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex, SelectCommand.SelectCommandType.PERSON);

        /* Case: select the current selected person card -> selected */
        assertCommandSuccess(command, middleIndex, SelectCommand.SelectCommandType.PERSON);

        /* --------------------- Perform select operations on the shown filtered person list ------------------------ */

        /* Case: filtered person list, select index within bounds of MeetingBook but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getMeetingBook().getPersonList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of MeetingBook and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredPersonList().size());
        command = SelectCommand.COMMAND_WORD + " p/" + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex, SelectCommand.SelectCommandType.PERSON);

        /* -------------------------------- Perform invalid select person operations -------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* -------------------- Perform select operations on the shown unfiltered group list ------------------------ */

        /* Case: select the first card in the group list, command with leading spaces and trailing spaces
         * -> selected
         */
        command = "   " + SelectCommand.COMMAND_WORD + " g/" + INDEX_FIRST_GROUP.getOneBased() + "    ";
        assertCommandSuccess(command, INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP);

        /* Case: select the last card in the group list -> selected */
        Index groupCount = getGroupLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " g/" + groupCount.getOneBased();
        assertCommandSuccess(command, groupCount, SelectCommand.SelectCommandType.GROUP);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last group card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the group list -> selected */
        middleIndex = getGroupMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " g/" + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex, SelectCommand.SelectCommandType.GROUP);

        /* Case: select the current selected group card -> selected */
        assertCommandSuccess(command, middleIndex, SelectCommand.SelectCommandType.GROUP);

        /* --------------------- Perform select operations on the shown filtered group list ------------------------- */
        // TODO implement find group
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MeetingBookSystemTest#assertSelectedPersonCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex,
                                      SelectCommand.SelectCommandType selectType) {
        Model expectedModel = getModel();
        String expectedResultMessage = (selectType == SelectCommand.SelectCommandType.GROUP)
                ? String.format(MESSAGE_SELECT_GROUP_SUCCESS, expectedSelectedCardIndex.getOneBased())
                : String.format(MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = (selectType == SelectCommand.SelectCommandType.GROUP)
                ? getGroupListPanel().getSelectedCardIndex()
                : getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (selectType == SelectCommand.SelectCommandType.PERSON) {
            assertPersonListDisplaysExpected(expectedModel);
            if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
                assertSelectedPersonCardUnchanged();

            } else {
                assertSelectedPersonCardChanged(expectedSelectedCardIndex);
            }
        } else {
            assertGroupListDisplaysExpected(expectedModel);
            if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
                assertSelectedGroupCardUnchanged();
            } else {
                assertSelectedGroupCardChanged(expectedSelectedCardIndex);
            }
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedPersonCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
