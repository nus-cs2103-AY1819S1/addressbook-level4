package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BIG;
import static seedu.thanepark.testutil.TypicalRides.DUMBO;
import static seedu.thanepark.testutil.TypicalRides.GALAXY;

import org.junit.Test;
import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.FilterCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.model.Model;

public class FilterCommandSystemTest extends ThaneParkSystemTest {
    private static final String LESS_THAN_TEN_STRING = " < 10";
    private static final String MORE_THAN_THIRTY_STRING = " > 30";
    private static final String MORE_THAN_EQUAL_TEN_STRING = " >= 10";
    private static final String LESS_THAN_EQUAL_THIRTY_STRING = " <= 30";

    @Test
    public void filter() {
        /* Case: filter multiple rides in thane park with a single waitTime condition, command with leading spaces and
        trailing spaces
         * -> 3 rides found
         */
        String command = "    " + FilterCommand.COMMAND_WORD + " " + PREFIX_WAITING_TIME + LESS_THAN_TEN_STRING + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ACCELERATOR, DUMBO, GALAXY);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous command where ride list is displaying the rides we are searching
         * -> 3 rides found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_WAITING_TIME + LESS_THAN_TEN_STRING;
        assertCommandSuccess(command, expectedModel);
        assertStatusBarUnchanged();

        /* Case: Filter one ride with multiple conditions -> 2 rides found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_WAITING_TIME + LESS_THAN_EQUAL_THIRTY_STRING + " "
                + PREFIX_MAINTENANCE + MORE_THAN_THIRTY_STRING;
        ModelHelper.setFilteredList(expectedModel, BIG, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertStatusBarUnchanged();

        /* Case: the arguments in filter has spaces -> 2 rides found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_WAITING_TIME + " < = 30 " + PREFIX_MAINTENANCE + "   >   "
                + "30";
        ModelHelper.setFilteredList(expectedModel, BIG, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertStatusBarUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter commant -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same rides in thane park after deleting 1 of them -> 1 ride found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getThanePark().getRideList().contains(BIG));
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_WAITING_TIME + LESS_THAN_EQUAL_THIRTY_STRING + " "
                + PREFIX_MAINTENANCE + MORE_THAN_THIRTY_STRING;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_RIDES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_RIDES_LISTED_OVERVIEW, expectedModel.getFilteredRideList().size());
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
