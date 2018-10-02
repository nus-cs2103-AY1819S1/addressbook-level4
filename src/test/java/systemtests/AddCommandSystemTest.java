package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalCards.KEYWORD_MATCHING_WHAT;
import static seedu.address.testutil.TypicalCards.Q_APP_MADE;
import static seedu.address.testutil.TypicalCards.Q_CS2103_PROF;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.Q_RANDOM_QUESTION;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.CardUtil;

public class AddCommandSystemTest extends AppSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a card to a non-empty trivia bundle, command with leading spaces and trailing spaces
         * -> added
         */
        Card toAdd = Q_FLAT_EARTH;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + QUESTION_DESC_EARTH_FLAT + "  "
                + ANSWER_DESC_EARTH_FLAT + " " + TAG_DESC_PHYSICS + " ";
        assertCommandSuccess(command, toAdd);

        // TODO To enable this after undo/redo command is implemented on trivia.
        //        /* Case: undo adding Amy to the list -> Amy deleted */
        //        command = UndoCommand.COMMAND_WORD;
        //        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        //        assertCommandSuccess(command, model, expectedResultMessage);
        //
        //        /* Case: redo adding Amy to the list -> Amy added again */
        //        command = RedoCommand.COMMAND_WORD;
        //        model.addPerson(toAdd);
        //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        //        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a card with all fields same as another card in the trivia bundle except question -> added */
        toAdd = new CardBuilder(Q_FLAT_EARTH).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        command = AddCommand.COMMAND_WORD + QUESTION_DESC_GIT_COMMIT + ANSWER_DESC_EARTH_FLAT
                + TAG_DESC_PHYSICS;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(Q_RANDOM_QUESTION);

        // TODO To enable this after clear command is implemented on trivia.
        //        /* Case: add to empty address book -> added */
        //        deleteAllPersons();
        //        assertCommandSuccess(ALICE);

        // TODO To enable this after clear command is implemented on trivia.
        /* Case: add a card with tags, command with parameters in random order -> added */
        //        toAdd = Q_GIT_COMMIT;
        //        command = AddCommand.COMMAND_WORD + TAG_DESC_GIT + QUESTION_DESC_GIT_COMMIT + ANSWER_DESC_GIT_COMMIT
        //                + TAG_DESC_PHYSICS;
        //        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the card list before adding -> added */
        showCardsWithQuestion(KEYWORD_MATCHING_WHAT);
        assertCommandSuccess(Q_CS2103_PROF);

        /* ------------------------ Perform add operation while a card is selected --------------------------- */

        /* Case: selects first card in the card list, add a card -> added, card selection remains unchanged */
        selectCard(Index.fromOneBased(1));
        assertCommandSuccess(Q_APP_MADE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate card -> rejected */
        command = CardUtil.getAddCommand(Q_FLAT_EARTH);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add a duplicate card except with different question -> rejected */
        toAdd = new CardBuilder(Q_FLAT_EARTH).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        command = CardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add a duplicate card except with different answer -> rejected */
        toAdd = new CardBuilder(Q_FLAT_EARTH).withAnswer(VALID_ANSWER_GIT_COMMIT).build();
        command = CardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add a duplicate cards except with different tags -> rejected */
        command = CardUtil.getAddCommand(Q_FLAT_EARTH) + " " + PREFIX_TAG.getPrefix() + "Time";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: missing question -> rejected */
        command = AddCommand.COMMAND_WORD + ANSWER_DESC_EARTH_FLAT + TAG_DESC_PHYSICS;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing answer -> rejected */
        command = AddCommand.COMMAND_WORD + QUESTION_DESC_EARTH_FLAT + TAG_DESC_PHYSICS;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + CardUtil.getCardDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid question -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_QUESTION_DESC + ANSWER_DESC_EARTH_FLAT + TAG_DESC_PHYSICS;
        assertCommandFailure(command, Question.MESSAGE_QUESTION_CONSTRAINTS);

        /* Case: invalid answer -> rejected */
        command = AddCommand.COMMAND_WORD + QUESTION_DESC_EARTH_FLAT + INVALID_ANSWER_DESC + TAG_DESC_PHYSICS;
        assertCommandFailure(command, Answer.MESSAGE_ANSWER_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + QUESTION_DESC_EARTH_FLAT + ANSWER_DESC_EARTH_FLAT
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code CardListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Card toAdd) {
        assertCommandSuccess(CardUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Card)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Card)
     */
    private void assertCommandSuccess(String command, Card toAdd) {
        Model expectedModel = getModel();
        expectedModel.addCard(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Card)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code CardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Card)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardViewUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code CardListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
