package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for (@code FindDepartmentCommand).
 */
public class FindDepartmentCommandTest {
    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DepartmentContainsKeywordsPredicate firstPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("first"));
        DepartmentContainsKeywordsPredicate secondPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("second"));

        FindDepartmentCommand findFirstCommand = new FindDepartmentCommand(firstPredicate);
        FindDepartmentCommand findSecondCommand = new FindDepartmentCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDepartmentCommand findFirstCommandCopy = new FindDepartmentCommand(firstPredicate);
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
        DepartmentContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindDepartmentCommand command = new FindDepartmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        DepartmentContainsKeywordsPredicate predicate = preparePredicate("Accounting Tech");
        FindDepartmentCommand command = new FindDepartmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, FIONA, GEORGE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code DepartmentContainsKeywordsPredicate}.
     */
    private DepartmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
