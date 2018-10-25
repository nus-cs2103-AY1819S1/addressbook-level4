package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EducationFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FilterByEducationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = "Sec";
        String second = " ";


        FilterByEducationCommand filterByEducationFirstCommand = new FilterByEducationCommand(first);
        FilterByEducationCommand filterByEducationSecondCommand = new FilterByEducationCommand(second);

        // same object -> returns true
        assertTrue(filterByEducationFirstCommand.equals(filterByEducationFirstCommand));

        // same values -> returns true
        FilterByEducationCommand filterByEducationFirstCommandCopy = new FilterByEducationCommand(first);
        assertTrue(filterByEducationFirstCommand.equals(filterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByEducationFirstCommand.equals(1));


        // different person -> returns false
        assertFalse(filterByEducationFirstCommand.equals(filterByEducationSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format("Cannot find Sec education within the students list!");
        EducationFilterPredicate predicate = new EducationFilterPredicate("Sec");
        FilterByEducationCommand command = new FilterByEducationCommand("Sec");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
