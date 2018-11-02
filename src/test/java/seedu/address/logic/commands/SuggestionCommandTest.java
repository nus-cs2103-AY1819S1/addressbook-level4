package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;

import org.junit.Test;



public class SuggestionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = "Alice";
        SuggestionCommand SuggestionFirstCommand = new SuggestionCommand(first);
        // same object -> returns true
        assertTrue(SuggestionFirstCommand.equals(SuggestionFirstCommand));

        // same values -> returns true
        SuggestionCommand SuggestionFirstCommandCopy = new SuggestionCommand(first);
        assertTrue(SuggestionFirstCommand.equals(SuggestionFirstCommandCopy));

        // different types -> returns false
        assertFalse(SuggestionFirstCommand.equals(1));

    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

