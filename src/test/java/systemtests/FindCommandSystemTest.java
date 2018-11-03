package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.parking.commons.core.Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW;
import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;
import static seedu.parking.testutil.TypicalCarparks.CHARLIE;
import static seedu.parking.testutil.TypicalCarparks.DELTA;
import static seedu.parking.testutil.TypicalCarparks.KEYWORD_MATCHING_SENGKANG;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.RedoCommand;
import seedu.parking.logic.commands.UndoCommand;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.tag.Tag;

public class FindCommandSystemTest extends CarparkFinderSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in car park finder, command with leading spaces and trailing spaces
         * -> 2 car parks found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SENGKANG + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BRAVO, DELTA); // addresses contains sengkang
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: repeat previous find command where car park list is displaying the car parks we are finding
         * -> 2 car parks found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SENGKANG;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find car park where car park list is not displaying the car park we are finding
         * -> 1 car park found
         */
        command = FindCommand.COMMAND_WORD + " U25";
        ModelHelper.setFilteredList(expectedModel, CHARLIE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: find multiple car parks in car park finder, 2 keywords -> 2 car parks found */
        command = FindCommand.COMMAND_WORD + " SK88 SK23";
        ModelHelper.setFilteredList(expectedModel, BRAVO, DELTA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: find multiple car parks in car park finder, 2 keywords in reversed order -> 2 car parks found */
        command = FindCommand.COMMAND_WORD + " SK23 SK88";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple car parks in car park finder, 2 keywords with 1 repeat -> 2 car parks found */
        command = FindCommand.COMMAND_WORD + " SK23 SK88 SK23";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple car parks in car park finder, 2 matching keywords and 1 non-matching keyword
         * -> 2 car parks found
         */
        command = FindCommand.COMMAND_WORD + " SK23 SK88 NonMatchingKeyWord";
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

        /* Case: find car park in car park finder, keyword is same as name but of different case
         * -> 1 car park found
         */
        command = FindCommand.COMMAND_WORD + " SeNgKaNg";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find car park in car park finder, keyword is substring of name -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " Sen";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find car park in car park finder, name is substring of keyword -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " Sengkangs";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: find car park not in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " AK47";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find type of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getCarparkType().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find coordinate of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getCoordinate().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find lots available of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getLotsAvailable().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find total lots of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getTotalLots().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find free parking of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getFreeParking().toString();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find night parking of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getNightParking().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find short term parking of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getShortTerm().toString();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find type of parking of car park in car park finder -> 0 car parks found */
        command = FindCommand.COMMAND_WORD + " " + DELTA.getTypeOfParking().toString();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of car park in car park finder -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DELTA.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a car park is selected -> selected card deselected */
        showAllCarparks();
        selectCarpark(Index.fromOneBased(1));
        assertFalse(getCarparkListPanel().getHandleToSelectedCard().getCarparkNumber().equals(
                DELTA.getCarparkNumber().toString()));
        command = FindCommand.COMMAND_WORD + " SK23";
        ModelHelper.setFilteredList(expectedModel, DELTA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: find car park in empty car park finder -> 0 persons found */
        deleteAllCarparks();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SENGKANG;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DELTA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChangedMulti(expectedModel.getFilteredCarparkList().toArray(new Carpark[]{}));

        /* Case: mixed case command word -> rejected */
        command = "FiNd Sengkang";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_CARPARKS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_CARPARKS_LISTED_OVERVIEW, expectedModel.getFilteredCarparkList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see CarparkFinderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
