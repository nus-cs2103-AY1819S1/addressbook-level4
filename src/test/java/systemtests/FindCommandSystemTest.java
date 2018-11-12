package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_WORDS_LISTED_OVERVIEW;
import static seedu.learnvocabulary.testutil.TypicalWords.DELIBERATE;
import static seedu.learnvocabulary.testutil.TypicalWords.FIRE;
import static seedu.learnvocabulary.testutil.TypicalWords.KEYWORD_MATCHING_WEIGHT;
import static seedu.learnvocabulary.testutil.TypicalWords.VOLCANO;
import static seedu.learnvocabulary.testutil.TypicalWords.WEIGHT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.commands.DeleteCommand;
import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.logic.commands.RedoCommand;
import seedu.learnvocabulary.logic.commands.UndoCommand;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;

public class FindCommandSystemTest extends LearnVocabularySystemTest {

    @Test
    public void find() {
        /* Case: find multiple words in LearnVocabulary, command with leading spaces and trailing spaces
         * -> 1 word found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WEIGHT + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, WEIGHT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where word list is displaying the words we are finding
         * -> 2 words found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WEIGHT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find word where LearnVocabulary is not displaying the word we are finding -> 1 word found */
        command = FindCommand.COMMAND_WORD + " fire";
        ModelHelper.setFilteredList(expectedModel, FIRE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple words in LearnVocabulary, 2 keywords -> 2 words found */
        command = FindCommand.COMMAND_WORD + " deliberate volcano";
        ModelHelper.setFilteredList(expectedModel, DELIBERATE, VOLCANO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple words in LearnVocabulary, 2 keywords in reversed order -> 2 words found */
        command = FindCommand.COMMAND_WORD + " volcano deliberate";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple words in LearnVocabulary, 2 keywords with 1 repeat -> 2 words found */
        command = FindCommand.COMMAND_WORD + " deliberate volcano deliberate";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple words in LearnVocabulary, 2 matching keywords and 1 non-matching keyword
         * -> 2 words found
         */
        command = FindCommand.COMMAND_WORD + " deliberate volcano NonMatchingKeyWord";
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

        /* Case: find same words in LearnVocabulary after deleting 1 of them -> 1 word found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getLearnVocabulary().getWordList().contains(DELIBERATE));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WEIGHT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, WEIGHT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find word in LearnVocabulary, keyword is same as name but of different case -> 1 word found */
        command = FindCommand.COMMAND_WORD + " weight";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find word in LearnVocabulary, keyword is substring of name -> 0 words found */
        command = FindCommand.COMMAND_WORD + " eight";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find word in LearnVocabulary, name is substring of keyword -> 0 words found */
        command = FindCommand.COMMAND_WORD + " ight";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find word not in LearnVocabulary -> 0 words found */
        command = FindCommand.COMMAND_WORD + " great";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of word in LearnVocabulary -> 0 words found */
        List<Tag> tags = new ArrayList<>(VOLCANO.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a word is selected -> selected card deselected */
        showAllWords();
        selectWord(Index.fromOneBased(1));
        assertFalse(getWordListPanel().getHandleToSelectedCard().getName().equals(VOLCANO.getName().fullName));
        command = FindCommand.COMMAND_WORD + " volcano";
        ModelHelper.setFilteredList(expectedModel, VOLCANO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find word in empty LearnVocabulary -> 0 words found */
        deleteAllWords();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_WEIGHT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, VOLCANO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd volcano"
                + "";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_WORDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_WORDS_LISTED_OVERVIEW, expectedModel.getFilteredWordList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
