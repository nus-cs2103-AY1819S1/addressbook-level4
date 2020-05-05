package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;
import ssp.scheduleplanner.model.task.TagsContainsAllKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterStrictCommand}.
 */
public class FilterStrictCommandTest {
    private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagsContainsAllKeywordsPredicate firstPredicate =
                new TagsContainsAllKeywordsPredicate(Collections.singletonList("first"));
        TagsContainsAllKeywordsPredicate secondPredicate =
                new TagsContainsAllKeywordsPredicate(Collections.singletonList("second"));

        FilterStrictCommand filterFirstCommand = new FilterStrictCommand(firstPredicate);
        FilterStrictCommand filterSecondCommand = new FilterStrictCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterStrictCommand findFirstCommandCopy = new FilterStrictCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TagsContainsAllKeywordsPredicate predicate = preparePredicate(" ");
        FilterStrictCommand command = new FilterStrictCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_singleTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TagsContainsAllKeywordsPredicate predicate = preparePredicate("friends owesMoney");
        FilterStrictCommand command = new FilterStrictCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredTaskList(), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code TagsContainsAllKeywordsPredicate}.
     */
    private TagsContainsAllKeywordsPredicate preparePredicate(String userInput) {
        return new TagsContainsAllKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
