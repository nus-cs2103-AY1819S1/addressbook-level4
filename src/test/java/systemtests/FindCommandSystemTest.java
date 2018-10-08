package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalCards.KEYWORD_MATCHING_WHAT;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_FORCE_FORMULA;
import static seedu.address.testutil.TypicalCards.Q_GIT_MERGE;
import static seedu.address.testutil.TypicalCards.Q_TALLEST_BUILDING;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AppSystemTest {

    @Test
    public void find() {
        /* Case: find multiple cards in trivia bundle, command with leading spaces and trailing spaces
         * -> 4 cards found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHAT + "   ";
        Model expectedModel = getModel();
        // questions has a what in it
        ModelHelper.setFilteredList(expectedModel, true, Q_GIT_MERGE, Q_FORCE_FORMULA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: repeat previous find command where card list is displaying the cards we are finding
         * -> 2 cards found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WHAT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find card where card list is not displaying the card we are finding -> 1 card found */
        command = FindCommand.COMMAND_WORD + " Why";
        ModelHelper.setFilteredList(expectedModel, true, Q_EARTH_ROUND);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find multiple cards in trivia bundle, 2 keywords -> 2 cards found */
        command = FindCommand.COMMAND_WORD + " earth merge";
        ModelHelper.setFilteredList(expectedModel, true, Q_EARTH_ROUND, Q_GIT_MERGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find multiple cards in trivia bundle, 2 keywords in reversed order -> 2 cards found */
        command = FindCommand.COMMAND_WORD + " merge earth";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find multiple cards in trivia bundle, 2 keywords with 1 repeat -> 2 cards found */
        command = FindCommand.COMMAND_WORD + " earth merge earth";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find multiple cards in trivia bundle, 2 matching keywords and 1 non-matching keyword
         * -> 2 cards found
         */
        command = FindCommand.COMMAND_WORD + " earth merge NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        // TODO To enable this after undo/redo command is implemented on trivia.
        //        /* Case: undo previous find command -> rejected */
        //        command = UndoCommand.COMMAND_WORD;
        //        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        //        assertCommandFailure(command, expectedResultMessage);
        //
        //        /* Case: redo previous find command -> rejected */
        //        command = RedoCommand.COMMAND_WORD;
        //        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        //        assertCommandFailure(command, expectedResultMessage);

        // TODO To enable this after delete command is implemented on trivia.
        /* Case: find same cards in trivia bundle after deleting 1 of them -> 1 card found */
        //        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        //        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        //        expectedModel = getModel();
        //        ModelHelper.setFilteredList(expectedModel, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();

        /* Case: find card in trivia bundle, keyword is same as name but of different case -> 1 card found */
        command = FindCommand.COMMAND_WORD + " wHaT";
        ModelHelper.setFilteredList(expectedModel, true, Q_GIT_MERGE, Q_FORCE_FORMULA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find card in trivia bundle, keyword is substring of question -> 0 cards found */
        command = FindCommand.COMMAND_WORD + " wha";
        ModelHelper.setFilteredList(expectedModel, true);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find card in trivia bundle, question is substring of keyword -> 0 cards found */
        command = FindCommand.COMMAND_WORD + " whatssup";
        ModelHelper.setFilteredList(expectedModel, true);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find card not in trivia bundle -> 0 cards found */
        command = FindCommand.COMMAND_WORD + " NotInTriviaBundle";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find answer of card in trivia bundle -> 0 cards found */
        command = FindCommand.COMMAND_WORD + " " + Q_TALLEST_BUILDING.getAnswer().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find tags of card in trivia bundle -> 0 cards found */
        List<Tag> tags = new ArrayList<>(Q_EARTH_ROUND.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardViewUnchanged();

        /* Case: find while a card is selected -> selected card deselected */
        showAllCards();
        selectCard(Index.fromOneBased(1));
        assertFalse(getCardListPanel().getHandleToSelectedCard().getQuestion()
                .equals(Q_GIT_MERGE.getQuestion().value));
        command = FindCommand.COMMAND_WORD + " merge";
        ModelHelper.setFilteredList(expectedModel, true, Q_GIT_MERGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        // TODO To enable this after clear command is implemented on trivia.
        /* Case: find person in empty address book -> 0 persons found */
        //        deleteAllPersons();
        //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        //        expectedModel = getModel();
        //        ModelHelper.setFilteredList(expectedModel, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardViewUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_CARDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_CARDS_LISTED_OVERVIEW, expectedModel.getFilteredCardList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardViewUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
