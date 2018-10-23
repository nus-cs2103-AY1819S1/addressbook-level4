package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeFilterPredicate;

public class FilterByTimeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = new String("Mon 1200 1400");
        String second = new String(" ");


        FilterByTimeCommand filterByTimeFirstCommand = new FilterByTimeCommand(first);
        FilterByTimeCommand filterByGradeSecondCommand = new FilterByTimeCommand(second);

        // same object -> returns true
        assertTrue(filterByTimeFirstCommand.equals(filterByTimeFirstCommand));

        // same values -> returns true
        FilterByTimeCommand filterByEducationFirstCommandCopy = new FilterByTimeCommand(first);
        assertTrue(filterByTimeFirstCommand.equals(filterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByTimeFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(filterByTimeFirstCommand.equals(filterByGradeSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Time time = new Time("Mon 1200 1400");
        TimeFilterPredicate predicate = new TimeFilterPredicate(time);
        FilterByTimeCommand command = new FilterByTimeCommand("Mon 1200 1400");
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
