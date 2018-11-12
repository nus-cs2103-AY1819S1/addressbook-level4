package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradeFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;


public class FilterByGradeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = "20 90";
        FilterByGradeCommand filterByGradeFirstCommand = new FilterByGradeCommand(first);
        // same object -> returns true
        assertTrue(filterByGradeFirstCommand.equals(filterByGradeFirstCommand));

        // same values -> returns true
        FilterByGradeCommand filterByGradeFirstCommandCopy = new FilterByGradeCommand(first);
        assertTrue(filterByGradeFirstCommand.equals(filterByGradeFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByGradeFirstCommand.equals(1));

    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {


        String expectedMessage = String.format("Cannot find person whose grade between 0.0 and 0.0 !");
        GradeFilterPredicate predicate = new GradeFilterPredicate(0, 0);

        FilterByGradeCommand command = new FilterByGradeCommand("0 0");

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
