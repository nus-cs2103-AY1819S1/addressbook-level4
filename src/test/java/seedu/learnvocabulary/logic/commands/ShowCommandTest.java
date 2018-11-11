package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.FIRE;
import static seedu.learnvocabulary.testutil.TypicalWords.GLIDE;
import static seedu.learnvocabulary.testutil.TypicalWords.HURRICANE;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ShowCommandTest {
    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        ShowCommand showFirstCommand = new ShowCommand(firstPredicate);
        ShowCommand showSecondCommand = new ShowCommand(secondPredicate);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowCommand findFirstCommandCopy = new ShowCommand(firstPredicate);
        assertTrue(showFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different word -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noWordFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredWordList(predicate);
        assertCommandSuccess(command, model, commandHistory, ShowCommand.COMMAND_NO_WORDS_FOUND, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWordList());
    }

    @Test
    public void execute_multipleKeywords_multipleWordsFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("fire glide hurricane");
        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredWordList(predicate);
        String expectedMessage =
                ShowCommand.toString(expectedModel.getFilteredWordList());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIRE, HURRICANE, GLIDE), model.getFilteredWordList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
