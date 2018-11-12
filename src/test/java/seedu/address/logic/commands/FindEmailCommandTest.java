package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for (@code FindEmailCommand).
 */
public class FindEmailCommandTest {
    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEmailCommand findFirstCommand = new FindEmailCommand(firstPredicate);
        FindEmailCommand findSecondCommand = new FindEmailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEmailCommand findFirstCommandCopy = new FindEmailCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EmailContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        EmailContainsKeywordsPredicate predicate = preparePredicate("daniel@example.com");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
