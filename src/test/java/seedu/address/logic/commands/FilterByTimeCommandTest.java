package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
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
    public void equals() throws ParseException {
        String first = "ts/mon 1200 1400";
        FilterByTimeCommand filterByTimeFirstCommand = new FilterByTimeCommand(first);

        // same object -> returns true
        assertTrue(filterByTimeFirstCommand.equals(filterByTimeFirstCommand));

        // same values -> returns true
        FilterByTimeCommand filterByTimeFirstCommandCopy = new FilterByTimeCommand(first);
        assertTrue(filterByTimeFirstCommand.equals(filterByTimeFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByTimeFirstCommand.equals(1));

    }



    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
