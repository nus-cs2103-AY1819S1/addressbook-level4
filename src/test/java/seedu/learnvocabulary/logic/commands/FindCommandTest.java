package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_WORDS_LISTED_OVERVIEW;
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
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different word -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noWordFound() {
        String expectedMessage = String.format(MESSAGE_WORDS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredWordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWordList());
    }

    @Test
    public void execute_multipleKeywords_multipleWordsFound() {
        String expectedMessage = String.format(MESSAGE_WORDS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("fire glide hurricane");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredWordList(predicate);
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
