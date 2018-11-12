package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradeFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Time;

public class FilterByTimeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() throws ParseException {
        Time first = new Time("mon 1200 1400");
        FilterByTimeCommand filterByTimeFirstCommand = new FilterByTimeCommand(first);

        // same object -> returns true
        assertTrue(filterByTimeFirstCommand.equals(filterByTimeFirstCommand));

        // same values -> returns true
        FilterByTimeCommand filterByTimeFirstCommandCopy = new FilterByTimeCommand(first);
        assertTrue(filterByTimeFirstCommand.equals(filterByTimeFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByTimeFirstCommand.equals(1));

        Time second = new Time("mon 1300 1400");
        FilterByTimeCommand filterByTimeSecondCommand = new FilterByTimeCommand(second);

        // same object -> returns true
        assertTrue(filterByTimeSecondCommand.equals(filterByTimeSecondCommand));

        // same values -> returns true
        FilterByTimeCommand filterByTimeSecondCommandCopy = new FilterByTimeCommand(second);
        assertTrue(filterByTimeSecondCommand.equals(filterByTimeSecondCommandCopy));

        // different types -> returns false
        assertFalse(filterByTimeSecondCommand.equals(1));

    }

    @Test
    public void executeZeroKeywordsNoPersonFound() throws ParseException {

        Time time = new Time("mon 0000 0100");
        String expectedMessage = String.format("Cannot find " + time.toString() + " slot within the students list!");
        GradeFilterPredicate predicate = new GradeFilterPredicate(0, 0);

        FilterByTimeCommand command = new FilterByTimeCommand(new Time("mon 0000 0100"));

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        Time timeS = new Time("mon 0000 0400");
        String expectedMessageS = String.format("Cannot find " + time.toString() + " slot within the students list!");
        GradeFilterPredicate predicateS = new GradeFilterPredicate(0, 0);

        FilterByTimeCommand commandS = new FilterByTimeCommand(new Time("mon 0000 0400"));

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
