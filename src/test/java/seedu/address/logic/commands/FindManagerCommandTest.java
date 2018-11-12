package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ManagerContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for (@code FindManagerCommand).
 */
public class FindManagerCommandTest {
    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ManagerContainsKeywordsPredicate firstPredicate =
                new ManagerContainsKeywordsPredicate(Collections.singletonList("first"));
        ManagerContainsKeywordsPredicate secondPredicate =
                new ManagerContainsKeywordsPredicate(Collections.singletonList("second"));

        FindManagerCommand findFirstCommand = new FindManagerCommand(firstPredicate);
        FindManagerCommand findSecondCommand = new FindManagerCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindManagerCommand findFirstCommandCopy = new FindManagerCommand(firstPredicate);
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
        ManagerContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindManagerCommand command = new FindManagerCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ManagerContainsKeywordsPredicate predicate = preparePredicate("Moses Marcus");
        FindManagerCommand command = new FindManagerCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code ManagerContainsKeywordsPredicate}.
     */
    private ManagerContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ManagerContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
