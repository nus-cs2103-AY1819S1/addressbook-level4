package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BIG;
import static seedu.thanepark.testutil.TypicalRides.CASTLE;
import static seedu.thanepark.testutil.TypicalRides.DUMBO;
import static seedu.thanepark.testutil.TypicalRides.KEYWORD_MATCHING_THE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.tag.Tag;

public class FindCommandSystemTest extends ThaneParkSystemTest {

    @Test
    public void find() {
        /* Case: find multiple rides in thane park, command with leading spaces and trailing spaces
         * -> 2 rides found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_THE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BIG, DUMBO); // names of BIG and DUMBO contains "The"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where ride list is displaying the rides we are finding
         * -> 2 rides found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_THE;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find ride where ride list is not displaying the ride we are finding -> 1 ride found */
        command = FindCommand.COMMAND_WORD + " CASTLE";
        ModelHelper.setFilteredList(expectedModel, CASTLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple rides in thane park, 2 keywords -> 2 rides found */
        command = FindCommand.COMMAND_WORD + " Big Elephant";
        ModelHelper.setFilteredList(expectedModel, BIG, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple rides in thane park, 2 keywords in reversed order -> 2 rides found */
        command = FindCommand.COMMAND_WORD + " Dumbo Big";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple rides in thane park, 2 keywords with 1 repeat -> 2 rides found */
        command = FindCommand.COMMAND_WORD + " Dumbo Big Dumbo";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple rides in thane park, 2 matching keywords and 1 non-matching keyword
         * -> 2 rides found
         */
        command = FindCommand.COMMAND_WORD + " Dumbo Big NonMatchingKeyWord";
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

        /* Case: find same rides in thane park after deleting 1 of them -> 1 ride found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getThanePark().getRideList().contains(BIG));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_THE;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find ride in thane park, keyword is same as name but of different case -> 1 ride found */
        command = FindCommand.COMMAND_WORD + " tHe";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find ride in thane park, keyword is substring of name -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " th";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find ride in thane park, name is substring of keyword -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " their";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find ride not in thane park -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of ride in thane park -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " " + DUMBO.getDaysSinceMaintenance().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find thanepark of ride in thane park -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " " + DUMBO.getZone().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find thanepark of ride in thane park -> 1 rides found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_ZONE + DUMBO.getZone().value;
        ModelHelper.setFilteredList(expectedModel, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find waiting time of ride in thane park -> 0 rides found */
        command = FindCommand.COMMAND_WORD + " " + DUMBO.getWaitingTime().toString();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of ride in thane park -> 0 rides found */
        List<Tag> tags = new ArrayList<>(DUMBO.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of ride in thane park -> 3 rides found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ACCELERATOR, DUMBO);
        assertCommandSuccess(command, expectedModel);
        // assertSelectedCardUnchanged();

        /* Case: find while a ride is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DUMBO.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Dumbo";
        ModelHelper.setFilteredList(expectedModel, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find ride in empty thane park -> 0 rides found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_THE;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DUMBO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
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
