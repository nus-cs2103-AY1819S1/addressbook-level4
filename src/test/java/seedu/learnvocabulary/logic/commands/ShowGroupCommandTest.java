package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
//@@author Harryqu123
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ShowGroupCommandTest {
    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        ShowGroupCommand showFirstCommand = new ShowGroupCommand(firstPredicate);
        ShowGroupCommand showSecondCommand = new ShowGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowGroupCommand findFirstCommandCopy = new ShowGroupCommand(firstPredicate);
        assertTrue(showFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different word -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }
    @Test
    public void execute_randomKeywords_zeroWordFound() {
        TagContainsKeywordsPredicate predicate = preparePredicate("abc");
        ShowGroupCommand command = new ShowGroupCommand(predicate);
        expectedModel.updateTag(predicate);
        assertCommandSuccess(command, model, commandHistory, "Group abc has been shown", expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredWordList());
    }
    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput));
    }
}
//@@author
