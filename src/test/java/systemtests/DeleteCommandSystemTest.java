package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.scheduler.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.scheduler.logic.commands.DeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.scheduler.testutil.TestUtil.getEvent;
import static seedu.scheduler.testutil.TestUtil.getLastIndex;
import static seedu.scheduler.testutil.TestUtil.getMidIndex;
import static seedu.scheduler.testutil.TypicalEvents.KEYWORD_MATCHING_STARTUP;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.RedoCommand;
import seedu.scheduler.logic.commands.UndoCommand;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;

public class DeleteCommandSystemTest extends SchedulerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first event in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_EVENT.getOneBased() + "       ";
        Event deletedEvent = removeEvent(expectedModel, INDEX_FIRST_EVENT);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent.getEventName());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last event in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastEventIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastEventIndex);

        /* Case: undo deleting the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last event in the list -> last event deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeEvent(modelBeforeDeletingLast, lastEventIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle event in the list -> deleted */
        Index middleEventIndex = getMidIndex(getModel());
        assertCommandSuccess(middleEventIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered event list, delete index within bounds of scheduler and event list -> deleted */
        showEventsWithEventName(KEYWORD_MATCHING_STARTUP);
        Index index = INDEX_FIRST_EVENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredEventList().size());
        assertCommandSuccess(index);

        /* Case: filtered event list, delete index within bounds of scheduler but out of bounds of event list
         * -> rejected
         */
        showEventsWithEventName(KEYWORD_MATCHING_STARTUP);
        int invalidIndex = getModel().getScheduler().getEventList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        //remove for v1.3.1 for the readme page will change thr URL
        ///* ---------------------Performing delete operation while a event card is selected ------------------------ */
        //
        //        /* Case: delete the selected event -> event list panel selects the event before the deleted event */
        //
        //        showAllEvents();
        //        expectedModel = getModel();
        //        Index selectedIndex = getLastIndex(expectedModel);
        //        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        //        selectEvent(selectedIndex);
        //        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        //        deletedEvent = removeEvent(expectedModel, selectedIndex);
        //        expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent.getEventName());
        //        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getScheduler().getEventList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Event} at the specified {@code index} in {@code model}'s scheduler.
     * @return the removed event
     */
    private Event removeEvent(Model model, Index index) {
        Event targetEvent = getEvent(model, index);
        model.deleteEvent(targetEvent);
        return targetEvent;
    }

    /**
     * Deletes the event at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Event deletedEvent = removeEvent(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent.getEventName());

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
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see SchedulerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

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
     * {@code SchedulerBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
