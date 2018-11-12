package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Time;

public class FilterByTimeCommandTest {

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

    }



    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
