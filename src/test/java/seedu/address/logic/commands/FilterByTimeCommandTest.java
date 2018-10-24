package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradeFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeFilterPredicate;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class FilterByTimeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = new String("Mon 1200 1400");
        String second = new String(" ");


        FilterByTimeCommand FilterByTimeFirstCommand = new FilterByTimeCommand(first);
        FilterByTimeCommand FilterByGradeSecondCommand = new FilterByTimeCommand(second);

        // same object -> returns true
        assertTrue(FilterByTimeFirstCommand.equals(FilterByTimeFirstCommand));

        // same values -> returns true
        FilterByTimeCommand FilterByEducationFirstCommandCopy = new FilterByTimeCommand(first);
        assertTrue(FilterByTimeFirstCommand.equals(FilterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterByTimeFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(FilterByTimeFirstCommand.equals(FilterByGradeSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        Time time = new Time("mon 2400 0100");
        String expectedMessage = String.format("Cannot find " + time.toString() +" education within the students list!");
        TimeFilterPredicate predicate = new TimeFilterPredicate(time);
        FilterByTimeCommand command = new FilterByTimeCommand("mon 2400 0100");
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
