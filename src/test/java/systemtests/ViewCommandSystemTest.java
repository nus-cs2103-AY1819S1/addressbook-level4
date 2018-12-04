package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.thanepark.logic.commands.ViewCommand.MESSAGE_SELECT_RIDE_SUCCESS;
import static seedu.thanepark.testutil.TestUtil.getLastIndex;
import static seedu.thanepark.testutil.TestUtil.getMidIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.testutil.TypicalRides.KEYWORD_MATCHING_THE;

import java.io.IOException;

import org.junit.Test;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.logic.commands.ViewCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;

public class ViewCommandSystemTest extends ThaneParkSystemTest {
    @Test
    public void select() throws IOException {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the ride list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_RIDE);

        /* Case: select the last card in the ride list -> selected */
        Index rideCount = getLastIndex(getModel());
        command = ViewCommand.COMMAND_WORD + " " + rideCount.getOneBased();
        assertCommandSuccess(command, rideCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the ride list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = ViewCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered ride list, select index within bounds of thanepark book but out of bounds of ride list
         * -> rejected
         */
        showRidesWithName(KEYWORD_MATCHING_THE);
        int invalidIndex = getModel().getThanePark().getRideList().size();
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
        /* Case: filtered ride list, select index within bounds of thanepark book and ride list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredRideList().size());
        command = ViewCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredRideList().size() + 1;
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("VieW 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty thanepark book -> rejected */
        deleteAllRides();
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased(),
                MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected ride.<br>
     * 4. {@code Storage} and {@code RideListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see ThaneParkSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) throws IOException {
        Model expectedModel = getModel();
        Ride expectedRide = expectedModel.getFilteredRideList().get(expectedSelectedCardIndex.getZeroBased());
        String expectedName = expectedRide.getName().fullName;
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_RIDE_SUCCESS, expectedName, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getRideListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RideListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
