package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.thanepark.logic.commands.DeleteCommand.MESSAGE_DELETE_RIDE_SUCCESS;
import static seedu.thanepark.testutil.TestUtil.getLastIndex;
import static seedu.thanepark.testutil.TestUtil.getMidIndex;
import static seedu.thanepark.testutil.TestUtil.getRide;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.testutil.TypicalRides.KEYWORD_MATCHING_THE;

import java.io.IOException;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;

public class DeleteCommandSystemTest extends ThaneParkSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() throws IOException {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first ride in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_RIDE.getOneBased() + "       ";
        Ride deletedRide = removeRide(expectedModel, INDEX_FIRST_RIDE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RIDE_SUCCESS, deletedRide);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last ride in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastRideIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastRideIndex);

        /* Case: undo deleting the last ride in the list -> last ride restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last ride in the list -> last ride deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeRide(modelBeforeDeletingLast, lastRideIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle ride in the list -> deleted */
        Index middleRideIndex = getMidIndex(getModel());
        assertCommandSuccess(middleRideIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered ride list, delete index within bounds of thanepark book and ride list -> deleted */
        showRidesWithName(KEYWORD_MATCHING_THE);
        Index index = INDEX_FIRST_RIDE;
        assertTrue(index.getZeroBased() < getModel().getFilteredRideList().size());
        assertCommandSuccess(index);

        /* Case: filtered ride list, delete index within bounds of thanepark book but out of bounds of ride list
         * -> rejected
         */
        showRidesWithName(KEYWORD_MATCHING_THE);
        int invalidIndex = getModel().getThanePark().getRideList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a ride card is selected ------------------------ */

        /* Case: delete the selected ride -> ride list panel selects the ride before the deleted ride */
        showAllRides();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectRide(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedRide = removeRide(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_RIDE_SUCCESS, deletedRide);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getThanePark().getRideList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Ride} at the specified {@code index} in {@code model}'s thanepark book.
     * @return the removed ride
     */
    private Ride removeRide(Model model, Index index) {
        Ride targetRide = getRide(model, index);
        model.deleteRide(targetRide);
        return targetRide;
    }

    /**
     * Deletes the ride at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) throws IOException {
        Model expectedModel = getModel();
        Ride deletedRide = removeRide(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RIDE_SUCCESS, deletedRide);

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
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage)
        throws IOException {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see ThaneParkSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) throws IOException {
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
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
